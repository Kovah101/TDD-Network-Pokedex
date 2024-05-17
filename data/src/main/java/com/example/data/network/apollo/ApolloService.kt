package com.example.data.network.apollo

import android.os.Looper
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApolloService {

    fun getApolloClient() : ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "ApolloClient must be initialized on the main thread"
        }
        val BASE_URL = "https://beta.pokeapi.co/graphql/v1beta"
        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}