package ru.relation.onetoone

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OwnerEntity::class, DogEntity::class], version = 1, exportSchema = false)
abstract class DogAndOwnerDatabase : RoomDatabase() {
    abstract fun dogAndOwnerDao(): DogAndOwnerDao

    companion object {
        private val DATABASE_NAME = "dogsandowners.db"
        fun getInstance(context: Context): DogAndOwnerDatabase {
            return Room.databaseBuilder(context, DogAndOwnerDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}