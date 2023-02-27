package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.network.PokeService
import com.example.data.network.PokemonResponse
import com.example.database.PokemonDAO
import com.example.database.PokemonDatabase
import com.example.repositories.PokemonRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
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

    // function to return different mock service responses
//    private val mockServiceError = object : PokeService{
//        override suspend fun getOriginalPokemon(): Response<PokemonResponse> {
//            return Response.error(500, ResponseBody.create(""))
//        }
//    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        testService = com.example.data.network.PokeService.pokeService
        testService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(com.example.data.network.PokeService.NonStrictJsonSerializer.serializer.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient.Builder().build())
            .build()
            .create(com.example.data.network.PokeService::class.java)

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, com.example.database.PokemonDatabase::class.java).build()
        pokemonDAO = db.pokemonDao()
        repository = com.example.repositories.PokemonRepositoryImpl(pokemonDAO, testService)
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