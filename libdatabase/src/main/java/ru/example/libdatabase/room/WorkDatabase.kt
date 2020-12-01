package ru.example.libdatabase.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WorkEntity::class], version = 1, exportSchema = false)
abstract class WorkDatabase : RoomDatabase() {
    abstract fun workDao(): WorkDao

    companion object {
        private val DATABASE_NAME = "work.db"
        fun getInstance(context: Context): WorkDatabase {
            return Room.databaseBuilder(context, WorkDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
