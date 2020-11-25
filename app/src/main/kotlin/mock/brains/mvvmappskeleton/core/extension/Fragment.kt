@file:Suppress("unchecked_cast")

package mock.brains.mvvmappskeleton.core.extension

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mock.brains.mvvmappskeleton.BuildConfig
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

fun <T, LD : LiveData<T>> Fragment.observeNullable(liveData: LD, onChanged: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer {
        onChanged(it)
    })
}

fun <T, LD : LiveData<T>> Fragment.observe(liveData: LD, onChanged: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer {
        it?.let(onChanged)
    })
}

fun <T, LD : LiveData<T>> Fragment.observeSingle(liveData: LD, onChanged: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            onChanged.invoke(t)
            liveData.removeObserver(this)
        }
    })
}

fun Fragment.getFragmentTag(suffix: String? = null): String = this::class.java.simpleName + (suffix ?: "")

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    activity?.toast(text, duration)
}

fun Fragment.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    activity?.toast(resId, duration)
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

inline fun Fragment.debug(code: () -> Unit) {
    if (BuildConfig.DEBUG) {
        code()
    }
}

inline fun <reified T : ViewModel> Fragment.sharedGraphViewModel(
    graphId: Int,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy {
    val clazz: KClass<T> = T::class

    val viewModelProvider = ViewModelProvider(findNavController().getViewModelStoreOwner(graphId), object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = getKoin().get(clazz, qualifier, parameters)
    })

    if (qualifier != null) {
        viewModelProvider.get(qualifier.value, clazz.java)
    } else {
        viewModelProvider.get(clazz.java)
    }
}