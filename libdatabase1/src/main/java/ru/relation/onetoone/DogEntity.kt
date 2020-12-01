package ru.relation.onetoone

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DogEntity.TABLE_NAME)
data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "ownerId")
    val ownerId: Long,
    @ColumnInfo(name = "name")
    val name: String
) {
    companion object {
        const val TABLE_NAME = "dogs"
    }
}