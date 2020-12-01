package ru.example.libdatabase.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WorkDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "work.sql.db"
        private const val DB_VERSION = 1

        // создаем таблицы
        private const val SQL_CREATE_TABLE_WORKS = "CREATE TABLE IF NOT EXISTS ${WorkEntity.TABLE_NAME} (" +
                "${WorkEntity.COLUMN__ID} INTEGER PRIMARY KEY," +
                "${WorkEntity.COLUMN_TITLE} TEXT," +
                "${WorkEntity.COLUMN_DESCRIPTION} TEXT)"

        // удаляем таблицы
        private const val SQL_DELETE_TABLE_WORKS = "DROP TABLE IF EXISTS ${WorkEntity.TABLE_NAME}"
    }

    val workDao: WorkDao by lazy { WorkDao(this) }

    override fun onCreate(db: SQLiteDatabase?) {
        // вызывается если БД не существует
        db?.execSQL(SQL_CREATE_TABLE_WORKS);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // старые версии не поддерживаются
        db?.execSQL(SQL_DELETE_TABLE_WORKS);
        onCreate(db);
    }
}
