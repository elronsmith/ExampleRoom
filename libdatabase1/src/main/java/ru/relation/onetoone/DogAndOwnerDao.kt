package ru.relation.onetoone

import androidx.room.*

@Dao
interface DogAndOwnerDao {
    @Query("SELECT * FROM ${OwnerEntity.TABLE_NAME} WHERE id = :id")
    abstract fun getOwnerById(id: Long): OwnerEntity?

    @Query("SELECT * FROM ${DogEntity.TABLE_NAME} WHERE id = :id")
    abstract fun getDogById(id: Long): DogEntity?

    @Transaction
    @Query("SELECT * FROM ${DogEntity.TABLE_NAME} WHERE ownerId = :ownerId")
    abstract fun getDogByOwnerId(ownerId: Long): DogEntity?

    @Transaction
    open fun setOwnerAndDog(owner: OwnerEntity, dog: DogEntity) {
        setOwner(owner)
        setDog(dog)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setOwner(owner: OwnerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setDog(dog: DogEntity)

    @Transaction
    open fun delete(ownerEntity: OwnerEntity, dogEntity: DogEntity) {
        delete(ownerEntity)
        delete(dogEntity)
    }

    @Delete
    abstract fun delete(owner: OwnerEntity)

    @Delete
    abstract fun delete(dog: DogEntity)

    @Transaction
    open fun deleteAll() {
        deleteAllDogs()
        deleteAllOwners()
    }

    @Query("DELETE FROM ${OwnerEntity.TABLE_NAME} WHERE id = :ownerId")
    abstract fun deleteOwnerById(ownerId: Long)

    @Query("DELETE FROM ${DogEntity.TABLE_NAME} WHERE id = :dogId")
    abstract fun deleteDogById(dogId: Long)

    @Query("DELETE FROM ${DogEntity.TABLE_NAME}")
    abstract fun deleteAllDogs()

    @Query("DELETE FROM ${OwnerEntity.TABLE_NAME}")
    abstract fun deleteAllOwners()

    @Query("SELECT COUNT(id) FROM ${OwnerEntity.TABLE_NAME}")
    abstract fun getSizeOwners(): Int

    @Query("SELECT COUNT(id) FROM ${DogEntity.TABLE_NAME}")
    abstract fun getSizeDogs(): Int

    @Transaction
    @Query("SELECT * FROM ${OwnerEntity.TABLE_NAME}")
    abstract fun getOwnerAndDogList(): List<OwnerAndDog>

    @Transaction
    open fun getOwnerAndDogByOwnerId(ownerId: Long): OwnerAndDog? {
        val owner = getOwnerById(ownerId)
        val dog = getDogByOwnerId(ownerId)
        return if (owner != null && dog != null)
            OwnerAndDog(owner, dog)
        else
            null
    }
}