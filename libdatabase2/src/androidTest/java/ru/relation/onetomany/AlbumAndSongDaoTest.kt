package ru.relation.onetomany

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumAndSongDaoTest {
    lateinit var database: MusicDatabase
    lateinit var dao: AlbumAndSongDao

    @Before
    fun beforeTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = MusicDatabase.getInstance(appContext)
        dao = database.albumAndSongDao()
        dao.deleteAll()
    }

    @Test
    fun getAlbums_isCorrect() {
        var list = dao.getAlbums()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_album1 = AlbumEntity(1, "album1")
        val test_album2 = AlbumEntity(2, "album2")
        dao.setAlbum(test_album1)
        dao.setAlbum(test_album2)

        list = dao.getAlbums()
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)

        with(list[0]) {
            Assert.assertEquals(test_album1.id, id)
            Assert.assertEquals(test_album1.name, name)
        }
        with(list[1]) {
            Assert.assertEquals(test_album2.id, id)
            Assert.assertEquals(test_album2.name, name)
        }

        dao.delete(test_album1)
        Assert.assertEquals(1, dao.getSizeAlbums())
    }

    @Test
    fun getAlbumById_isCorrect() {
        Assert.assertNull(dao.getAlbumById(1))

        val test_album1 = AlbumEntity(1, "album1")
        val test_album2 = AlbumEntity(2, "album2")
        dao.setAlbum(test_album1)
        dao.setAlbum(test_album2)
        Assert.assertEquals(2, dao.getSizeAlbums())

        val album1 = dao.getAlbumById(1)
        Assert.assertNotNull(album1)
        Assert.assertEquals(test_album1.id, album1!!.id)
        Assert.assertEquals(test_album1.name, album1.name)

        dao.deleteAlbum(1)
        Assert.assertEquals(1, dao.getSizeAlbums())
    }

    @Test
    fun getSongsByAlbumId_isCorrect() {
        var list = dao.getSongsByAlbumId(1)
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_album1 = AlbumEntity(1, "album1")
        val test_song1 = SongEntity(1, test_album1.id, "song1")
        val test_song2 = SongEntity(2, test_album1.id, "song2")
        dao.setAlbum(test_album1)
        dao.setSong(test_song1)
        dao.setSong(test_song2)

        Assert.assertEquals(1, dao.getSizeAlbums())
        Assert.assertEquals(2, dao.getSizeSongs())
        Assert.assertEquals(2, dao.getSizeSongsByAlbumId(test_album1.id))

        list = dao.getSongsByAlbumId(1)
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)
        with(list[0]) {
            Assert.assertEquals(test_song1.id, id)
            Assert.assertEquals(test_song1.albumId, albumId)
            Assert.assertEquals(test_song1.name, name)
        }
        with(list[1]) {
            Assert.assertEquals(test_song2.id, id)
            Assert.assertEquals(test_song2.albumId, albumId)
            Assert.assertEquals(test_song2.name, name)
        }

        dao.delete(test_song1)
        Assert.assertEquals(1, dao.getSizeSongsByAlbumId(test_album1.id))

        dao.setSong(test_song1)
        Assert.assertEquals(2, dao.getSizeSongsByAlbumId(test_album1.id))

        dao.deleteSong(test_song1.id)
        Assert.assertEquals(1, dao.getSizeSongsByAlbumId(test_album1.id))
    }

    @Test
    fun getAlbumWithSongs_isCorrect() {
        var list = dao.getAlbumWithSongs()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        val test_album = AlbumEntity(1, "album1")
        val test_song1 = SongEntity(1, test_album.id, "song1")
        val test_song2 = SongEntity(2, test_album.id, "song2")
        dao.setAlbum(test_album)
        dao.setSong(test_song1)
        dao.setSong(test_song2)

        Assert.assertEquals(1, dao.getSizeAlbums())
        Assert.assertEquals(2, dao.getSizeSongs())
        Assert.assertEquals(2, dao.getSizeSongsByAlbumId(test_album.id))

        list = dao.getAlbumWithSongs()
        Assert.assertNotNull(list)
        Assert.assertEquals(1, list.size)
        with(list[0]) {
            Assert.assertEquals(test_album.name, albumEntity.name)
            Assert.assertEquals(2, songs.size)
        }

        dao.delete(test_album)
        dao.deleteAllSongsByAlbumId(test_album.id)
        list = dao.getAlbumWithSongs()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        Assert.assertEquals(0, dao.getSizeAlbums())
        Assert.assertEquals(0, dao.getSizeSongs())
        Assert.assertEquals(0, dao.getSizeSongsByAlbumId(test_album.id))
    }

    @Test
    fun getAlbumWithSongsById_isCorrect() {
        var list = dao.getAlbumWithSongs()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        Assert.assertNull(dao.getAlbumWithSongsById(1))

        val test_album = AlbumEntity(1, "album1")
        val test_song1 = SongEntity(1, test_album.id, "song1")
        val test_song2 = SongEntity(2, test_album.id, "song2")
        dao.setAlbum(test_album)
        dao.setSong(test_song1)
        dao.setSong(test_song2)

        Assert.assertEquals(1, dao.getSizeAlbums())
        Assert.assertEquals(2, dao.getSizeSongs())
        Assert.assertEquals(2, dao.getSizeSongsByAlbumId(test_album.id))

        val albumWithSongs = dao.getAlbumWithSongsById(test_album.id)
        Assert.assertNotNull(albumWithSongs)
        Assert.assertEquals(test_album.name, albumWithSongs!!.albumEntity.name)
        Assert.assertEquals(2, albumWithSongs.songs.size)

        dao.delete(test_album)
        dao.deleteAllSongsByAlbumId(test_album.id)
        Assert.assertNull(dao.getAlbumWithSongsById(1))
        list = dao.getAlbumWithSongs()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        Assert.assertEquals(0, dao.getSizeAlbums())
        Assert.assertEquals(0, dao.getSizeSongs())
        Assert.assertEquals(0, dao.getSizeSongsByAlbumId(test_album.id))
    }
}