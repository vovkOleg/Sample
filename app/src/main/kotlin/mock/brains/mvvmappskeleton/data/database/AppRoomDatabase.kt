package mock.brains.mvvmappskeleton.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mock.brains.mvvmappskeleton.data.database.dao.CoreDao
import mock.brains.mvvmappskeleton.data.database.entity.CoreEntity

const val DATABASE_VERSION = 1

@Database(
    entities = [
        CoreEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun coreDao(): CoreDao

}