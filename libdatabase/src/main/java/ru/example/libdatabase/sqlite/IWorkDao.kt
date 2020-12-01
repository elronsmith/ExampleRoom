package ru.example.libdatabase.sqlite

interface IWorkDao {
    fun getWorks(): List<WorkEntity>

    fun getWork(id: Long): WorkEntity?

    fun setWork(work: WorkEntity)

    fun deleteWork(work: WorkEntity)

    fun deleteWork(id: Long)

    fun deleteAll()

    fun getSize(): Long
}