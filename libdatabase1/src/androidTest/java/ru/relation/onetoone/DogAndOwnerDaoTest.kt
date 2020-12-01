package ru.relation.onetoone

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DogAndOwnerDaoTest {
    lateinit var database: DogAndOwnerDatabase
    lateinit var dao: DogAndOwnerDao

    @Before
    fun beforeTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = DogAndOwnerDatabase.getInstance(appContext)
        dao = database.dogAndOwnerDao()
        dao.deleteAll()
    }

    @Test
    fun getOwnerById_isCorrect() {
        Assert.assertNull(dao.getOwnerById(1))

        val test_owner1 = OwnerEntity(1, "owner1")
        val test_owner2 = OwnerEntity(2, "owner2")
        dao.setOwner(test_owner1)
        dao.setOwner(test_owner2)

        val owner = dao.getOwnerById(1)
        Assert.assertNotNull(owner)
        Assert.assertEquals(test_owner1.id, owner!!.id)
        Assert.assertEquals(test_owner1.name, owner.name)

        dao.delete(owner)
        Assert.assertNull(dao.getOwnerById(1))

        dao.setOwner(test_owner1)
        Assert.assertNotNull(dao.getOwnerById(1))

        dao.deleteOwnerById(1)
        Assert.assertNull(dao.getOwnerById(1))

        Assert.assertEquals(1, dao.getSizeOwners())
    }

    @Test
    fun getDogById_isCorrect() {
        Assert.assertNull(dao.getDogById(1))
        Assert.assertNull(dao.getDogByOwnerId(1))

        val test_owner = OwnerEntity(1, "owner1")
        val test_dog = DogEntity(1, test_owner.id, "dog1")
        dao.setDog(test_dog)

        var dog = dao.getDogById(1)
        Assert.assertNotNull(dog)
        Assert.assertEquals(test_dog.id, dog!!.id)
        Assert.assertEquals(test_dog.name, dog.name)

        dog = dao.getDogByOwnerId(test_dog.ownerId)
        Assert.assertNotNull(dog)
        Assert.assertEquals(test_dog.id, dog!!.id)
        Assert.assertEquals(test_dog.name, dog.name)

        dao.delete(dog)
        Assert.assertNull(dao.getDogById(1))
        Assert.assertNull(dao.getDogByOwnerId(1))

        dao.setDog(test_dog)
        Assert.assertNotNull(dao.getDogById(1))

        dao.deleteDogById(1)
        Assert.assertNull(dao.getDogById(1))
        Assert.assertNull(dao.getDogByOwnerId(1))

        Assert.assertEquals(0, dao.getSizeDogs())
    }

    @Test
    fun getOwnerAndDogList_isCorrect() {
        var list = dao.getOwnerAndDogList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_owner = OwnerEntity(1, "owner1")
        val test_dog = DogEntity(1, test_owner.id, "dog1")

        dao.setOwnerAndDog(test_owner, test_dog)
        list = dao.getOwnerAndDogList()
        Assert.assertNotNull(list)
        Assert.assertEquals(1, list.size)
        with(list[0]) {
            Assert.assertEquals(test_owner.name, owner?.name)
            Assert.assertEquals(test_dog.name, dog?.name)
        }

        dao.deleteAll()
        list = dao.getOwnerAndDogList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)
    }

    @Test
    fun getDogAndOwnerByOwnerId_isCorrect() {
        var list = dao.getOwnerAndDogList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_owner = OwnerEntity(1, "owner1")
        val test_dog = DogEntity(1, test_owner.id, "dog1")

        dao.setOwnerAndDog(test_owner, test_dog)
        list = dao.getOwnerAndDogList()
        Assert.assertNotNull(list)
        Assert.assertEquals(1, list.size)
        with(list[0]) {
            Assert.assertEquals(test_owner.name, owner?.name)
            Assert.assertEquals(test_dog.name, dog?.name)
        }

        val dogAndOwner = dao.getOwnerAndDogByOwnerId(1)
        Assert.assertNotNull(list)
        Assert.assertEquals(test_owner.name, dogAndOwner?.owner?.name)
        Assert.assertEquals(test_dog.name, dogAndOwner?.dog?.name)

        dao.delete(dogAndOwner!!.owner!!, dogAndOwner!!.dog!!)

        list = dao.getOwnerAndDogList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)
    }
}