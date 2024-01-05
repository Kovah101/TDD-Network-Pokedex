package com.example.data.network.apollo

import com.apollographql.apollo3.ApolloClient

interface ApolloService {

    companion object {
        private const val TAG = "ApolloService"

        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
            .build()
    }
}