package ru.relation.manytomany

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GroupWithUserNamesV2(
    @Embedded
    val group: GroupEntity,
    @Relation(
        parentColumn = "groupId",
        entity = UserEntity::class,
        entityColumn = "userId",
        associateBy = Junction(UserGroupEntity::class),
    )
    val userNames: List<UserName>
)