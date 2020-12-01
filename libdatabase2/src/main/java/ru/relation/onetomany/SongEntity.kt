package ru.relation.onetomany

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SongEntity.TABLE_NAME)
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "albumId")
    val albumId: Long,
    @ColumnInfo(name = "name")
    val name: String
) {
    companion object {
        const val TABLE_NAME = "songs"
    }
}