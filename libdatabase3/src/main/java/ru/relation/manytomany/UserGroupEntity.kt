package ru.relation.manytomany

import androidx.room.Entity

@Entity(tableName = UserGroupEntity.TABLE_NAME, primaryKeys = ["userId", "groupId"])
data class UserGroupEntity(
    val userId: Long,
    val groupId: Long
) {
    companion object {
        const val TABLE_NAME = "usergroups"
    }
}
