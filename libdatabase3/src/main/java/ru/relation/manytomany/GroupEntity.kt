package ru.relation.manytomany

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GroupEntity.TABLE_NAME)
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "groupId")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String
) {
    companion object {
        const val TABLE_NAME = "groups"
    }
}
