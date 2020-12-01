package ru.relation.manytomany

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserAndGroupDaoTest {
    lateinit var database: UserGroupDatabase
    lateinit var dao: UserAndGroupDao

    @Before
    fun beforeTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = UserGroupDatabase.getInstance(appContext)
        dao = database.userAndGroupDao()
        dao.deleteAll()
    }

    @Test
    fun getUserById_isCorrect() {
        var list = dao.getUserWithGroupsList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        Assert.assertNull(dao.getUserById(1))

        val test_user1 = UserEntity(1, "user1")
        val test_user2 = UserEntity(2, "user2")
        dao.setUser(test_user1)
        dao.setUser(test_user2)

        val user1 = dao.getUserById(1)
        val user2 = dao.getUserById(2)
        Assert.assertNotNull(user1)
        Assert.assertNotNull(user2)
        Assert.assertEquals(test_user1.name, user1!!.name)
        Assert.assertEquals(test_user2.name, user2!!.name)

        dao.delete(user1)
        Assert.assertNull(dao.getUserById(1))
        Assert.assertNotNull(dao.getUserById(2))

        dao.deleteUserById(user2.id)
        Assert.assertNull(dao.getUserById(1))
        Assert.assertNull(dao.getUserById(2))
    }

    @Test
    fun getGroupById_isCorrect() {
        var list = dao.getUserWithGroupsList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        Assert.assertNull(dao.getGroupById(1))

        val test_group1 = GroupEntity(1, "group1")
        val test_group2 = GroupEntity(2, "group2")
        dao.setGroup(test_group1)
        dao.setGroup(test_group2)

        val group1 = dao.getGroupById(1)
        val group2 = dao.getGroupById(2)
        Assert.assertNotNull(group1)
        Assert.assertNotNull(group2)
        Assert.assertEquals(test_group1.name, group1!!.name)
        Assert.assertEquals(test_group2.name, group2!!.name)

        dao.delete(group1)
        Assert.assertNull(dao.getGroupById(1))
        Assert.assertNotNull(dao.getGroupById(2))

        dao.deleteGroupById(group2.id)
        Assert.assertNull(dao.getGroupById(1))
        Assert.assertNull(dao.getGroupById(2))
    }

    @Test
    fun getUserGroup_isCorrect() {
        var list = dao.getUserWithGroupsList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        Assert.assertNull(dao.getUserGroup(1, 1))

        dao.setUserGroupEntity(UserGroupEntity(1, 1))
        dao.setUserGroupEntity(UserGroupEntity(2, 1))
        dao.setUserGroupEntity(UserGroupEntity(2, 2))

        var userGroup = dao.getUserGroup(1, 1)
        Assert.assertNotNull(userGroup)
        Assert.assertEquals(1, userGroup!!.userId)
        Assert.assertEquals(1, userGroup.groupId)

        userGroup = dao.getUserGroup(2, 1)
        Assert.assertNotNull(userGroup)
        Assert.assertEquals(2, userGroup!!.userId)
        Assert.assertEquals(1, userGroup.groupId)

        userGroup = dao.getUserGroup(2, 2)
        Assert.assertNotNull(userGroup)
        Assert.assertEquals(2, userGroup!!.userId)
        Assert.assertEquals(2, userGroup.groupId)

        dao.deleteUserGroup(1, 1)
        Assert.assertNull(dao.getUserGroup(1, 1))
        Assert.assertNotNull(dao.getUserGroup(2, 1))
        Assert.assertNotNull(dao.getUserGroup(2, 2))

        dao.setUserGroupEntity(UserGroupEntity(1, 1))
        Assert.assertNotNull(dao.getUserGroup(1, 1))

        dao.deleteUserGroupByUserId(2)
        Assert.assertNotNull(dao.getUserGroup(1, 1))
        Assert.assertNull(dao.getUserGroup(2, 1))
        Assert.assertNull(dao.getUserGroup(2, 2))

        dao.setUserGroupEntity(UserGroupEntity(2, 1))
        dao.setUserGroupEntity(UserGroupEntity(2, 2))
        Assert.assertNotNull(dao.getUserGroup(2, 1))
        Assert.assertNotNull(dao.getUserGroup(2, 2))

        dao.deleteUserGroupByGroupId(1)
        Assert.assertNull(dao.getUserGroup(1, 1))
        Assert.assertNull(dao.getUserGroup(2, 1))
        Assert.assertNotNull(dao.getUserGroup(2, 2))
    }

    @Test
    fun getUserWithGroupsList_isCorrect() {
        var list = dao.getUserWithGroupsList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_user1 = UserEntity(1, "user1")
        val test_user2 = UserEntity(2, "user2")
        val test_group1 = GroupEntity(1, "group1")
        val test_group2 = GroupEntity(2, "group2")

        dao.setUser(test_user1)
        dao.setUser(test_user2)
        dao.setGroup(test_group1)
        dao.setGroup(test_group2)
        dao.setUserGroupEntity(UserGroupEntity(test_user1.id, test_group1.id))
        dao.setUserGroupEntity(UserGroupEntity(test_user2.id, test_group1.id))
        dao.setUserGroupEntity(UserGroupEntity(test_user2.id, test_group2.id))
        Assert.assertEquals(2, dao.getSizeUsers())
        Assert.assertEquals(2, dao.getSizeGroups())
        Assert.assertEquals(3, dao.getSizeUserGroups())

        list = dao.getUserWithGroupsList()
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)
        with(list[0]) {
            Assert.assertEquals(test_user1.name, user.name)
            Assert.assertEquals(1, groups.size)
            Assert.assertEquals(test_group1.name, groups[0].name)
        }

        with(list[1]) {
            Assert.assertEquals(test_user2.name, user.name)
            Assert.assertEquals(2, groups.size)
            Assert.assertEquals(test_group1.name, groups[0].name)
            Assert.assertEquals(test_group2.name, groups[1].name)
        }
    }

    @Test
    fun getUserWithGroupNamesList_isCorrect() {
        var list = dao.getUserWithGroupNamesList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_user1 = UserEntity(1, "user1")
        val test_user2 = UserEntity(2, "user2")
        val test_group1 = GroupEntity(1, "group1")
        val test_group2 = GroupEntity(2, "group2")

        dao.setUser(test_user1)
        dao.setUser(test_user2)
        dao.setGroup(test_group1)
        dao.setGroup(test_group2)
        dao.setUserGroupEntity(UserGroupEntity(test_user1.id, test_group1.id))
        dao.setUserGroupEntity(UserGroupEntity(test_user2.id, test_group1.id))
        dao.setUserGroupEntity(UserGroupEntity(test_user2.id, test_group2.id))
        Assert.assertEquals(2, dao.getSizeUsers())
        Assert.assertEquals(2, dao.getSizeGroups())
        Assert.assertEquals(3, dao.getSizeUserGroups())

        list = dao.getUserWithGroupNamesList()
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)
        with(list[0]) {
            Assert.assertEquals(test_user1.name, user.name)
            Assert.assertEquals(1, groupNames.size)
            Assert.assertEquals(test_group1.name, groupNames[0])
        }

        with(list[1]) {
            Assert.assertEquals(test_user2.name, user.name)
            Assert.assertEquals(2, groupNames.size)
            Assert.assertEquals(test_group1.name, groupNames[0])
            Assert.assertEquals(test_group2.name, groupNames[1])
        }
    }

    @Test
    fun getGroupWithUserNamesList_isCorrect() {
        var list = dao.getGroupWithUserNamesList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_user1 = UserEntity(1, "user1")
        val test_user2 = UserEntity(2, "user2")
        val test_group1 = GroupEntity(1, "group1")
        val test_group2 = GroupEntity(2, "group2")

        dao.setUser(test_user1)
        dao.setUser(test_user2)
        dao.setGroup(test_group1)
        dao.setGroup(test_group2)
        dao.setUserGroupEntity(UserGroupEntity(test_user1.id, test_group1.id))
        dao.setUserGroupEntity(UserGroupEntity(test_user2.id, test_group1.id))
        dao.setUserGroupEntity(UserGroupEntity(test_user2.id, test_group2.id))
        Assert.assertEquals(2, dao.getSizeUsers())
        Assert.assertEquals(2, dao.getSizeGroups())
        Assert.assertEquals(3, dao.getSizeUserGroups())

        list = dao.getGroupWithUserNamesList()
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)
        with(list[0]) {
            Assert.assertEquals(test_group1.name, group.name)
            Assert.assertEquals(2, userNames.size)
            Assert.assertEquals(test_user1.name, userNames[0])
            Assert.assertEquals(test_user2.name, userNames[1])
        }

        with(list[1]) {
            Assert.assertEquals(test_group2.name, group.name)
            Assert.assertEquals(1, userNames.size)
            Assert.assertEquals(test_user2.name, userNames[0])
        }
    }

    @Test
    fun getGroupWithUserNamesV2List_isCorrect() {
        var list = dao.getGroupWithUserNamesV2List()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_user1 = UserEntity(1, "user1")
        val test_user2 = UserEntity(2, "user2")
        val test_group1 = GroupEntity(1, "group1")
        val test_group2 = GroupEntity(2, "group2")

        dao.setUser(test_user1)
        dao.setUser(test_user2)
        dao.setGroup(test_group1)
        dao.setGroup(test_group2)
        dao.setUserGroupEntity(UserGroupEntity(test_user1.id, test_group1.id))
        dao.setUserGroupEntity(UserGroupEntity(test_user2.id, test_group1.id))
        dao.setUserGroupEntity(UserGroupEntity(test_user2.id, test_group2.id))
        Assert.assertEquals(2, dao.getSizeUsers())
        Assert.assertEquals(2, dao.getSizeGroups())
        Assert.assertEquals(3, dao.getSizeUserGroups())

        list = dao.getGroupWithUserNamesV2List()
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)
        with(list[0]) {
            Assert.assertEquals(test_group1.name, group.name)
            Assert.assertEquals(2, userNames.size)
            Assert.assertEquals(test_user1.id, userNames[0].userId)
            Assert.assertEquals(test_user1.name, userNames[0].name)
            Assert.assertEquals(test_user2.id, userNames[1].userId)
            Assert.assertEquals(test_user2.name, userNames[1].name)
        }

        with(list[1]) {
            Assert.assertEquals(test_group2.name, group.name)
            Assert.assertEquals(1, userNames.size)
            Assert.assertEquals(test_user2.id, userNames[0].userId)
            Assert.assertEquals(test_user2.name, userNames[0].name)
        }

    }
}