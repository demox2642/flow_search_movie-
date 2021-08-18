package com.skilbox.flowsearchmovie.database

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: MoviesDB
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            MoviesDB::class.java,
            MoviesDB.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
