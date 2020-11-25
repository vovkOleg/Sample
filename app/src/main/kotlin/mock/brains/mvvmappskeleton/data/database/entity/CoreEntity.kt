package mock.brains.mvvmappskeleton.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mock.brains.mvvmappskeleton.data.database.entity.CoreEntity.Companion.TABLE

@Entity(tableName = TABLE)
data class CoreEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: String
) {

    companion object {

        const val TABLE = "core"
        const val COLUMN_ID = "id"
    }
}