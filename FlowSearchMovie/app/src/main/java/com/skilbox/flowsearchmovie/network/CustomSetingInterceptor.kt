package com.skilbox.flowsearchmovie.network

import com.skilbox.flowsearchmovie.network.Network.MOVIE_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class CustomSetingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiRequest = originalRequest.newBuilder()
            .header("apikey", MOVIE_API_KEY)
            .build()
        return chain.proceed(modifiRequest)
    }
}
