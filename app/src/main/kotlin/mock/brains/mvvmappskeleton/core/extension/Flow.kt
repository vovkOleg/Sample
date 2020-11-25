package mock.brains.mvvmappskeleton.core.extension

import kotlinx.coroutines.flow.*
import mock.brains.mvvmappskeleton.core.network.CallState
import  mock.brains.mvvmappskeleton.core.network.Result

fun <LD, RD> Flow<Result<RD>>.flatMapRemoteLocal(
    localData: () -> Flow<LD>,
    predicateIfNeedSave: (suspend (RD) -> Unit)? = null,
    predicateIsNeedRemote: (suspend (LD?) -> Boolean)? = null
) = flow {

    emit(CallState.Loading())

    if (predicateIsNeedRemote != null && predicateIsNeedRemote(localData().first())) {
        collect { result ->
            when (result) {
                is Result.ResultSuccess -> {
                    result.body?.let { predicateIfNeedSave?.invoke(it) }
                    emitAll(localData().map { CallState.Success(it) })
                }
                is Result.ResultError -> {
                    emitAll(localData().map { CallState.Failure(result.exception) })
                }
            }
        }
    } else {
        emitAll(localData().map { CallState.Success(it) })
    }
}