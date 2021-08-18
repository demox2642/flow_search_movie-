package com.skilbox.flowsearchmovie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RemoteMovieContract.TABLE_NAME)
data class RemoteMovie(

    @PrimaryKey
    @ColumnInfo(name = RemoteMovieContract.Colums.ID)
    val id: String,
    @ColumnInfo(name = RemoteMovieContract.Colums.TITLE)
    val title: String,
    @ColumnInfo(name = RemoteMovieContract.Colums.YEAR)
    val year: String,
    @ColumnInfo(name = RemoteMovieContract.Colums.TYPE)
    val type: MovieType?,
    @ColumnInfo(name = RemoteMovieContract.Colums.POSTER)
    val poster: String
)
