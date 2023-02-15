package com.example.tddnetworkpokedex.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.tddnetworkpokedex.data.network.PokeService
import com.example.tddnetworkpokedex.data.network.PokemonResponse
import com.example.tddnetworkpokedex.database.PokemonDAO
import com.example.tddnetworkpokedex.database.PokemonDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import java.net.HttpURLConnection

@RunWith(RobolectricTestRunner::class)
class PokemonRepositoryImplTest {
    // Trying to test 200, 404, and 5xx http codes
    // conflicting tutorials online, currently doesn't use mockWebServer at all
    // possibly add single pokemon data retrieval for testing?

    private lateinit var repository: PokemonRepository
    private lateinit var testService: PokeService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var pokemonDAO: PokemonDAO
    private lateinit var db: PokemonDatabase
    private lateinit var actualResponse: Response<PokemonResponse>

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        testService = PokeService.pokeService
//        testService = Retrofit.Builder()
//            .baseUrl(mockWebServer.url("/").toString())
//            .addConverterFactory(PokeService.NonStrictJsonSerializer.serializer.asConverterFactory("application/json".toMediaType()))
//            .client(OkHttpClient.Builder().build())
//            .build()
//            .create(PokeService::class.java)

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java).build()
        pokemonDAO = db.pokemonDao()
        repository = PokemonRepositoryImpl(pokemonDAO, testService)
    }

    @After
    fun tearDown() {
        db.close()
        mockWebServer.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun OneFiveOne_Pokemon_Returned_With_TwoHundred_Http_Code() {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)

        mockWebServer.enqueue(expectedResponse)

        runBlocking {
            actualResponse = testService.getOriginalPokemon()
        }
        assert(actualResponse.body()?.result?.size == 151)
        assert(actualResponse.code() == HttpURLConnection.HTTP_OK)
    }
}