package com.skilbox.flowsearchmovie

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RemoteMovieDao {
    @Query("SELECT * FROM ${RemoteMovieContract.TABLE_NAME}")
    suspend fun getRemoteMovie(): List<RemoteMovie>

    @Query("select * from remouteMovie m where m.title like '%'||:title||'%' and m.type =:type ")
    suspend fun getMovie(title: String?, type: String): List<RemoteMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteMovie(movies: List<RemoteMovie>)

    @Delete
    suspend fun deleteRemoteMovie(movies: RemoteMovie)

    @Query("SELECT * FROM ${RemoteMovieContract.TABLE_NAME}")
    fun observeMovies(): Flow<List<RemoteMovie>>
}
