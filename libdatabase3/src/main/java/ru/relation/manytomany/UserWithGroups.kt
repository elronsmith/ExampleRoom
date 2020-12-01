package ru.relation.manytomany

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithGroups(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "groupId",
        associateBy = Junction(UserGroupEntity::class)
    )
    val groups: List<GroupEntity>
)