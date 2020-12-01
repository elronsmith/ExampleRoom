package ru.relation.onetomany

import androidx.room.Embedded
import androidx.room.Relation

class AlbumWithSongs(
    @Embedded
    val albumEntity: AlbumEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val songs: List<SongEntity>
)