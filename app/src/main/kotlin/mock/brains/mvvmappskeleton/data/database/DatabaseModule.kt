package mock.brains.mvvmappskeleton.data.database

import android.content.Context
import androidx.room.Room
import mock.brains.mvvmappskeleton.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val DATABASE_FILE_NAME = "${BuildConfig.APPLICATION_ID}.db"

val databaseModule = module {

    fun makeRoomDatabase(context: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            DATABASE_FILE_NAME
        ).fallbackToDestructiveMigration() // TODO change to proper migration in future
            .build()
    }

    // provide Room database
    single {
        makeRoomDatabase(androidApplication())
    }

    // provide DatabaseDataSource
    single {
        DatabaseDataSource(get())
    }
}