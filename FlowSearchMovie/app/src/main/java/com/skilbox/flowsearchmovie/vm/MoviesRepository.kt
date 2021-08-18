package com.skilbox.flowsearchmovie.vm

import android.util.Log
import com.skilbox.flowsearchmovie.MovieType
import com.skilbox.flowsearchmovie.RemoteMovie
import com.skilbox.flowsearchmovie.database.Database
import com.skilbox.flowsearchmovie.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MoviesRepository {
    private var movieList: List<RemoteMovie> = listOf()
    private var errorMassage: String = ""
    private val remoteMovieDao = Database.instance.remoteMovieDao()

    suspend fun searchMovie(
        type: MovieType?,
        text: String?
    ): List<RemoteMovie> {
        return suspendCoroutine { continuation ->
            val responce = text?.let { Network.getSearchMovieCall(it, type).execute() }

            val responeString = responce?.body?.string().orEmpty()
            val movies = parseMovieResponse(responeString)

            continuation.resume(movies)
        }
    }

    suspend fun loadMoviesToDB(movies: List<RemoteMovie>) {

        remoteMovieDao.insertRemoteMovie(movies)
    }

    private fun parseMovieResponse(responseBodyString: String): List<RemoteMovie> {

        try {
            val jsonObject = JSONObject(responseBodyString)
            val movieArrey = jsonObject.getJSONArray("Search")

            val moviesList = (0 until movieArrey.length()).map { index ->
                movieArrey.getJSONObject(index)
            }.map {
                val title = it.getString("Title")
                val year = it.getString("Year")
                val id = it.getString("imdbID")
                val type = when (it.getString("Type")) {
                    "movie" -> MovieType.MOVIE
                    "episode" -> MovieType.EPISODE
                    "series" -> MovieType.SERIES
                    else -> null
                }
                val poster = it.getString("Poster")

                RemoteMovie(id = id, title = title, year = year, type = type, poster = poster)
            }
            errorMassage = ""

            return moviesList
        } catch (e: JSONException) {

            errorMassage = e.message.toString()
            return emptyList()
        }
    }

    fun observeMovies(): Flow<Flow<List<RemoteMovie>>> {
        return flow {
            emit(remoteMovieDao.observeMovies())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllMovies(): List<RemoteMovie> {
        return remoteMovieDao.getRemoteMovie()
    }

    suspend fun getMovie(title: String?, type: String): List<RemoteMovie> {

        return remoteMovieDao.getMovie(title, type)
    }
}
