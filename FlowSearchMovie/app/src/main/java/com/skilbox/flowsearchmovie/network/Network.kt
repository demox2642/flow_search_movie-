package com.skilbox.flowsearchmovie.network

import android.util.Log
import com.skilbox.flowsearchmovie.MovieType
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object Network {

    val client: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(CustomSetingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    const val MOVIE_API_KEY = "1c8be216"

    fun getSearchMovieCall(text: String, type: MovieType?): Call {

        Log.e("getSearchMovieCall", "$text||$type")
        val url = HttpUrl.Builder()
            // http://www.omdbapi.com/?apikey=[yourkey]&s
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("apikey", MOVIE_API_KEY)
            .addQueryParameter("s", text)
            .addQueryParameter("type", type?.toString())
            .build()

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request)
    }
}
