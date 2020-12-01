package ru.relation.manytomany

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, GroupEntity::class, UserGroupEntity::class], version = 1, exportSchema = false)
abstract class UserGroupDatabase : RoomDatabase() {
    abstract fun userAndGroupDao(): UserAndGroupDao

    companion object {
        private val DATABASE_NAME = "user_group.db"
        fun getInstance(context: Context): UserGroupDatabase {
            return Room.databaseBuilder(context, UserGroupDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}