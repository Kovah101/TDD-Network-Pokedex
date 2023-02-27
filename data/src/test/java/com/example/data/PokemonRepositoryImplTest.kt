package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.network.PokeService
import com.example.data.network.PokemonDto
import com.example.data.network.PokemonResponse
import com.example.database.PokemonDAO
import com.example.database.PokemonDatabase
import com.example.repositories.PokemonRepository
import com.example.repositories.PokemonRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
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

    private lateinit var repository: PokemonRepository
    private lateinit var testService: PokeService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var pokemonDAO: PokemonDAO
    private lateinit var db: PokemonDatabase
    private lateinit var actualResponse: Response<PokemonResponse>


    // ADD HTTP enums
    private fun mockService(response: Int): PokeService {
        return when (response) {
            0 -> {
                object : PokeService {
                    override suspend fun getOriginalPokemon(): Response<PokemonResponse> {
                        return Response.success(
                            200,
                            PokemonResponse(
                                result = listOf(
                                    PokemonDto(name = "Electabuzz", url = "electabuzz.com"),
                                    PokemonDto(name = "Rhydon", url = "rhydon.com"),
                                    PokemonDto(name = "Lapras", url = "lapras.com"),
                                    PokemonDto(name = "Arcanine", url = "arcanine.com"),
                                    PokemonDto(name = "Exeggutor", url = "exeggutor.com"),
                                    PokemonDto(name = "aerodactyl", url = "aerodactyl.com"),
                                )
                            )
                        )
                    }
                }
            }
            1 -> {
                object : PokeService {
                    override suspend fun getOriginalPokemon(): Response<PokemonResponse> {
                        return Response.error(404, ResponseBody.create(contentType = null, "HTTP 404 Not found"))
                    }
                }
            }
            else -> {
                object : PokeService {
                    override suspend fun getOriginalPokemon(): Response<PokemonResponse> {
                        return Response.error(500, ResponseBody.create(null, "HTTP 500 Internal server error"))
                    }
                }
            }
        }
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        testService = mockService(response = 0)

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java)
            .build()
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
    fun Http200() {
        testService = mockService(response = 0)

        runBlocking {
            actualResponse = testService.getOriginalPokemon()
        }
        assert(actualResponse.body()?.result?.size == 6)
        assert(actualResponse.code() == HttpURLConnection.HTTP_OK)
    }

    @Test
    @Throws(Exception::class)
    fun Http400(){
        testService = mockService(response = 1)

        runBlocking {
            actualResponse = testService.getOriginalPokemon()
        }
        assert(actualResponse.code() == HttpURLConnection.HTTP_NOT_FOUND)
    }

    @Test
    @Throws(Exception::class)
    fun Http500(){
        testService = mockService(response = 3)

        runBlocking {
            actualResponse = testService.getOriginalPokemon()
        }
        assert(actualResponse.code() == HttpURLConnection.HTTP_INTERNAL_ERROR)
    }
}