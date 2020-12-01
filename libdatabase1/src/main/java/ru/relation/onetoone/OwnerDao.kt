package ru.relation.onetoone

import androidx.room.*

@Dao
abstract class OwnerDao {
    @Query("SELECT * FROM ${OwnerEntity.TABLE_NAME}")
    abstract fun getOwnersList(): List<OwnerEntity>

    @Query("SELECT * FROM ${OwnerEntity.TABLE_NAME} WHERE id = :id")
    abstract fun getOwnerById(id: Long): OwnerEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setOwner(owner: OwnerEntity)

    @Delete
    abstract fun delete(owner: OwnerEntity)

    @Query("DELETE FROM ${OwnerEntity.TABLE_NAME} WHERE id = :id")
    abstract fun delete(id: Long)

    @Query("DELETE FROM ${OwnerEntity.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("SELECT COUNT(id) FROM ${OwnerEntity.TABLE_NAME}")
    abstract fun getSize(): Int
}