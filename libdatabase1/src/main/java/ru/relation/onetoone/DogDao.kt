package ru.relation.onetoone

import androidx.room.*

@Dao
abstract class DogDao {
    @Query("SELECT * FROM ${DogEntity.TABLE_NAME}")
    abstract fun getDogsList(): List<DogEntity>

    @Query("SELECT * FROM ${DogEntity.TABLE_NAME} WHERE id = :id")
    abstract fun getDogById(id: Long): DogEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setDog(dog: DogEntity)

    @Delete
    abstract fun delete(dog: DogEntity)

    @Query("DELETE FROM ${DogEntity.TABLE_NAME} WHERE id = :id")
    abstract fun delete(id: Long)

    @Query("DELETE FROM ${DogEntity.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("SELECT COUNT(id) FROM ${DogEntity.TABLE_NAME}")
    abstract fun getSize(): Int
}