package ru.relation.manytomany

import androidx.room.*

@Dao
abstract class UserAndGroupDao {
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE userId = :userId")
    abstract fun getUserById(userId: Long): UserEntity?

    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME} WHERE groupId = :groupId")
    abstract fun getGroupById(groupId: Long): GroupEntity?

    @Query("SELECT * FROM ${UserGroupEntity.TABLE_NAME} WHERE userId = :userId AND groupId = :groupId")
    abstract fun getUserGroup(userId: Long, groupId: Long): UserGroupEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setGroup(group: GroupEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun setUserGroupEntity(userGroup: UserGroupEntity)

    @Delete
    abstract fun delete(user: UserEntity)

    @Delete
    abstract fun delete(group: GroupEntity)

    @Delete
    abstract fun delete(userGroup: UserGroupEntity)

    @Query("DELETE FROM ${UserEntity.TABLE_NAME} WHERE userId = :id")
    abstract fun deleteUserById(id: Long)

    @Query("DELETE FROM ${GroupEntity.TABLE_NAME} WHERE groupId = :id")
    abstract fun deleteGroupById(id: Long)

    @Query("DELETE FROM ${UserGroupEntity.TABLE_NAME} WHERE userId = :userId AND groupId = :groupId")
    abstract fun deleteUserGroup(userId: Long, groupId: Long)

    @Query("DELETE FROM ${UserGroupEntity.TABLE_NAME} WHERE userId = :userId")
    abstract fun deleteUserGroupByUserId(userId: Long)

    @Query("DELETE FROM ${UserGroupEntity.TABLE_NAME} WHERE groupId = :groupId")
    abstract fun deleteUserGroupByGroupId(groupId: Long)

    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    abstract fun deleteAllUsers()

    @Query("DELETE FROM ${GroupEntity.TABLE_NAME}")
    abstract fun deleteAllGroups()

    @Query("DELETE FROM ${UserGroupEntity.TABLE_NAME}")
    abstract fun deleteAllUserGroup()

    @Transaction
    open fun deleteAll() {
        deleteAllUsers()
        deleteAllGroups()
        deleteAllUserGroup()
    }

    @Query("SELECT COUNT(userId) FROM ${UserEntity.TABLE_NAME}")
    abstract fun getSizeUsers(): Int

    @Query("SELECT COUNT(groupId) FROM ${GroupEntity.TABLE_NAME}")
    abstract fun getSizeGroups(): Int

    @Query("SELECT COUNT(userId) FROM ${UserGroupEntity.TABLE_NAME}")
    abstract fun getSizeUserGroups(): Int

    @Transaction
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    abstract fun getUserWithGroupsList(): List<UserWithGroups>

    @Transaction
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    abstract fun getUserWithGroupNamesList(): List<UserWithGroupNames>

    @Transaction
    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    abstract fun getGroupWithUserNamesList(): List<GroupWithUserNames>

    @Transaction
    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    abstract fun getGroupWithUserNamesV2List(): List<GroupWithUserNamesV2>

}
