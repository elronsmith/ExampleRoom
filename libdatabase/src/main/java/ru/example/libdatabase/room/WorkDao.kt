package ru.example.libdatabase.room

import androidx.room.*

@Dao
abstract class WorkDao {
    @Query("SELECT * FROM ${WorkEntity.TABLE_NAME}")
    abstract fun getWorks(): List<WorkEntity>

    @Query("SELECT * FROM ${WorkEntity.TABLE_NAME} WHERE id = :id")
    abstract fun getWork(id: Long): WorkEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setWork(work: WorkEntity)

    @Delete
    abstract fun deleteWork(work: WorkEntity)

    @Query("DELETE FROM ${WorkEntity.TABLE_NAME} WHERE id = :id")
    abstract fun deleteWork(id: Long)

    @Query("DELETE FROM ${WorkEntity.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("SELECT COUNT(id) FROM ${WorkEntity.TABLE_NAME}")
    abstract fun getSize(): Int
}
