package ru.relation.manytomany

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "secondName")
    val secondName: String = "",
    @ColumnInfo(name = "thirdName")
    val thirdName: String = "",
    @ColumnInfo(name = "birthDate")
    val birthDate: Long = 0
) {
    companion object {
        const val TABLE_NAME = "users"
    }
}
