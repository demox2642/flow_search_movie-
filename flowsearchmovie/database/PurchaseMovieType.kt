package com.skilbox.flowsearchmovie.database

import androidx.room.TypeConverter
import com.skilbox.flowsearchmovie.MovieType

class PurchaseMovieType {

    @TypeConverter
    fun convertTypeToString(type: MovieType): String = type.name

    @TypeConverter
    fun convertStringToType(string: String): MovieType? {
        return when (string) {
            MovieType.MOVIE.name -> MovieType.MOVIE
            MovieType.SERIES.name -> MovieType.SERIES
            MovieType.EPISODE.name -> MovieType.EPISODE

            else -> null
        }
    }
}
