package com.skilbox.flowsearchmovie.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skilbox.flowsearchmovie.RemoteMovie
import com.skilbox.flowsearchmovie.RemoteMovieDao
import com.skilbox.flowsearchmovie.database.MoviesDB.Companion.DB_VERSION
import com.skilbox.shopdb.database.PurchaseBigDecimalConverter
import com.skilbox.shopdb.database.PurchaseDateConverter

@Database(
    entities = [
        RemoteMovie::class

    ],
    version = DB_VERSION
)

@TypeConverters(
    PurchaseMovieType::class,
    PurchaseDateConverter::class, PurchaseBigDecimalConverter::class
)

abstract class MoviesDB : RoomDatabase() {

    abstract fun remoteMovieDao(): RemoteMovieDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "MoviesDB"
    }
}
