package mock.brains.mvvmappskeleton.data.database.dao

import androidx.room.*
import mock.brains.mvvmappskeleton.data.database.entity.CoreEntity
import mock.brains.mvvmappskeleton.data.database.entity.CoreEntity.Companion.COLUMN_ID
import mock.brains.mvvmappskeleton.data.database.entity.CoreEntity.Companion.TABLE

@Dao
interface CoreDao {

    @Query("SELECT * from $TABLE WHERE $COLUMN_ID = :id")
    suspend fun get(id: String): CoreEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(entity: CoreEntity)

    @Update
    fun update(entity: CoreEntity)

    @Query("DELETE FROM $TABLE WHERE $COLUMN_ID = :id")
    fun delete(id: String)
}