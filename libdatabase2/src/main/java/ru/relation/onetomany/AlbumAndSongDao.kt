package ru.relation.onetomany

import androidx.room.*

@Dao
abstract class AlbumAndSongDao {
    @Query("SELECT * FROM ${AlbumEntity.TABLE_NAME}")
    abstract fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM ${AlbumEntity.TABLE_NAME} WHERE id = :id")
    abstract fun getAlbumById(id: Long): AlbumEntity?

    @Query("SELECT * FROM ${SongEntity.TABLE_NAME} WHERE albumId = :albumId")
    abstract fun getSongsByAlbumId(albumId: Long): List<SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setAlbum(albumEntity: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setSong(songEntity: SongEntity)

    @Delete
    abstract fun delete(albumEntity: AlbumEntity)

    @Delete
    abstract fun delete(songEntity: SongEntity)

    @Query("DELETE FROM ${AlbumEntity.TABLE_NAME} WHERE id = :id")
    abstract fun deleteAlbum(id: Long)

    @Query("DELETE FROM ${SongEntity.TABLE_NAME} WHERE albumId = :albumId")
    abstract fun deleteAllSongsByAlbumId(albumId: Long)

    @Query("DELETE FROM ${SongEntity.TABLE_NAME} WHERE id = :id")
    abstract fun deleteSong(id: Long)

    @Transaction
    open fun deleteAll() {
        deleteAllAlbums()
        deleteAllSongs()
    }

    @Query("DELETE FROM ${AlbumEntity.TABLE_NAME}")
    abstract fun deleteAllAlbums()

    @Query("DELETE FROM ${SongEntity.TABLE_NAME}")
    abstract fun deleteAllSongs()

    @Query("SELECT COUNT(id) FROM ${AlbumEntity.TABLE_NAME}")
    abstract fun getSizeAlbums(): Int

    @Query("SELECT COUNT(id) FROM ${SongEntity.TABLE_NAME}")
    abstract fun getSizeSongs(): Int

    @Query("SELECT COUNT(id) FROM ${SongEntity.TABLE_NAME} WHERE albumId = :albumId")
    abstract fun getSizeSongsByAlbumId(albumId: Long): Int

    @Transaction
    @Query("SELECT * FROM ${AlbumEntity.TABLE_NAME}")
    abstract fun getAlbumWithSongs(): List<AlbumWithSongs>

    @Transaction
    open fun getAlbumWithSongsById(albumId: Long): AlbumWithSongs? {
        val album = getAlbumById(albumId)
        val songs = getSongsByAlbumId(albumId)
        return if (album != null)
            AlbumWithSongs(album, songs)
        else
            null
    }
}