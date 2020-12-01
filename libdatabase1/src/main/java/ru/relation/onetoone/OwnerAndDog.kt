package ru.relation.onetoone

import androidx.room.Embedded
import androidx.room.Relation

data class OwnerAndDog(
    @Embedded
    val owner: OwnerEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "ownerId"
    )
    val dog: DogEntity?
)
