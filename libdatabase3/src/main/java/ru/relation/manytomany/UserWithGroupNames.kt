package ru.relation.manytomany

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithGroupNames(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entity = GroupEntity::class,
        entityColumn = "groupId",
        associateBy = Junction(UserGroupEntity::class),
        projection = ["name"]
    )
    val groupNames: List<String>
)