package ru.relation.onetoone

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = OwnerEntity.TABLE_NAME)
data class OwnerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String
) {
    companion object {
        const val TABLE_NAME = "owners"
    }
}