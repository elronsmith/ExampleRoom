package ru.example.libdatabase.sqlite

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkDatabaseTest {
    lateinit var database: WorkDatabase
    lateinit var dao: IWorkDao

    @Before
    fun beforeTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = WorkDatabase(appContext)
        dao = database.workDao
        dao.deleteAll()
    }

    @Test
    fun getWorks_isCorrect() {
        var list = dao.getWorks()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_work1 = WorkEntity(1, "title1", "description1")
        val test_work2 = WorkEntity(2, "title2", "description2")
        dao.setWork(test_work1)
        dao.setWork(test_work2)

        Assert.assertEquals(2, dao.getSize())
        list = dao.getWorks()
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)

        with(list[0]) {
            Assert.assertEquals(test_work1.id, id)
            Assert.assertEquals(test_work1.title, title)
            Assert.assertEquals(test_work1.description, description)
        }
        with(list[1]) {
            Assert.assertEquals(test_work2.id, id)
            Assert.assertEquals(test_work2.title, title)
            Assert.assertEquals(test_work2.description, description)
        }

        dao.deleteWork(test_work1)
        Assert.assertEquals(1, dao.getSize())
        list = dao.getWorks()
        Assert.assertNotNull(list)
        Assert.assertEquals(1, list.size)
        with(list[0]) {
            Assert.assertEquals(test_work2.id, id)
            Assert.assertEquals(test_work2.title, title)
            Assert.assertEquals(test_work2.description, description)
        }
    }

    @Test
    fun getWork_isCorrect() {
        var list = dao.getWorks()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        Assert.assertNull(dao.getWork(1))

        val test_work1 = WorkEntity(1, "title1", "description1")
        val test_work2 = WorkEntity(2, "title2", "description2")
        dao.setWork(test_work1)
        dao.setWork(test_work2)

        Assert.assertEquals(2, dao.getSize())
        list = dao.getWorks()
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)

        val work1 = dao.getWork(test_work1.id)
        Assert.assertNotNull(work1)
        Assert.assertEquals(test_work1.id, work1!!.id)
        Assert.assertEquals(test_work1.title, work1.title)
        Assert.assertEquals(test_work1.description, work1.description)

        dao.deleteWork(test_work1.id)
        Assert.assertEquals(1, dao.getSize())
        list = dao.getWorks()
        Assert.assertNotNull(list)
        Assert.assertEquals(1, list.size)

        Assert.assertNull(dao.getWork(test_work1.id))
    }
}
