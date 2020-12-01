package ru.example.libdatabase.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WorkEntity.TABLE_NAME)
data class WorkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,
) {
    companion object {
        const val TABLE_NAME = "works"
    }
}
