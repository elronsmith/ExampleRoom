package ru.relation.manytomany

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GroupWithUserNames(
    @Embedded
    val group: GroupEntity,
    @Relation(
        parentColumn = "groupId",
        entity = UserEntity::class,
        entityColumn = "userId",
        associateBy = Junction(UserGroupEntity::class),
        projection = ["name"]
    )
    val userNames: List<String>
)