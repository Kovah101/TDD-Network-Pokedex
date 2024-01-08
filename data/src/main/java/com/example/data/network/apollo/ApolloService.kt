package com.example.data.network.apollo

import android.os.Looper
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient

class ApolloService {

    fun getApolloClient() : ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "ApolloClient must be initialized on the main thread"
        }
        val BASE_URL = "https://beta.pokeapi.co/graphql/v1beta"
        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .build()

        return ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}