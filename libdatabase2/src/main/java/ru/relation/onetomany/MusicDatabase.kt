package ru.relation.onetomany

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SongEntity::class, AlbumEntity::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun albumAndSongDao(): AlbumAndSongDao

    companion object {
        private val DATABASE_NAME = "music.db"
        fun getInstance(context: Context): MusicDatabase {
            return Room.databaseBuilder(context, MusicDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}