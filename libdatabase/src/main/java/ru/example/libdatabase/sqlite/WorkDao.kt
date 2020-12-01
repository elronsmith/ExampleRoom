package ru.example.libdatabase.sqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteOpenHelper

class WorkDao(private val database: SQLiteOpenHelper): IWorkDao {
    private val projection = arrayOf(
        WorkEntity.COLUMN__ID,
        WorkEntity.COLUMN_TITLE,
        WorkEntity.COLUMN_DESCRIPTION
    )

    override fun getWorks(): List<WorkEntity> {
        val result = ArrayList<WorkEntity>()
        val cursor = database.writableDatabase.query(WorkEntity.TABLE_NAME, null, null, null, null, null, null)
        if (cursor.moveToFirst())
            do {
                result.add(obtainWorkEntity(cursor))
            } while (cursor.moveToNext())

        cursor.close()
        return result
    }

    override fun getWork(id: Long): WorkEntity? {
        val db = database.readableDatabase

        val selection = "${WorkEntity.COLUMN__ID} = ?"
        val selectionArgs = arrayOf("$id")

        val cursor = db.query(
            WorkEntity.TABLE_NAME,  // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,          // don't group the rows
            null,           // don't filter by row groups
            null           // The sort order
        )

        var entity: WorkEntity? = null
        if (cursor.moveToFirst()) {
            entity = WorkEntity(
                cursor.getLong(cursor.getColumnIndex(WorkEntity.COLUMN__ID)),
                cursor.getString(cursor.getColumnIndex(WorkEntity.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(WorkEntity.COLUMN_DESCRIPTION)))
        }

        cursor.close()
        return entity
    }

    override fun setWork(work: WorkEntity) {
        val contentValues = obtainContentValues(work)
        val rows = database.writableDatabase.update(
            WorkEntity.TABLE_NAME,
            contentValues,
            "${WorkEntity.COLUMN__ID}=?",
            arrayOf(work.id.toString()))
        if (rows == 0)
            database.writableDatabase.insert(
                WorkEntity.TABLE_NAME,
                null,
                contentValues)
    }

    override fun deleteWork(work: WorkEntity) {
        deleteWork(work.id)
    }

    override fun deleteWork(id: Long) {
        database.writableDatabase.delete(
            WorkEntity.TABLE_NAME,
            "${WorkEntity.COLUMN__ID}=?",
            arrayOf(id.toString())
        )
    }

    override fun deleteAll() {
        database.writableDatabase.delete(WorkEntity.TABLE_NAME, null, null)
    }

    override fun getSize(): Long {
        return DatabaseUtils.queryNumEntries(database.readableDatabase, WorkEntity.TABLE_NAME)
    }

    private fun obtainContentValues(work: WorkEntity): ContentValues {
        val result = ContentValues()
        result.put(WorkEntity.COLUMN__ID, work.id)
        result.put(WorkEntity.COLUMN_TITLE, work.title)
        result.put(WorkEntity.COLUMN_DESCRIPTION, work.description)

        return result
    }

    private fun obtainWorkEntity(cursor: Cursor): WorkEntity {
        return WorkEntity(
            cursor.getLong(cursor.getColumnIndex(WorkEntity.COLUMN__ID)),
            cursor.getString(cursor.getColumnIndex(WorkEntity.COLUMN_TITLE)),
            cursor.getString(cursor.getColumnIndex(WorkEntity.COLUMN_DESCRIPTION))
        )
    }
}