package com.example.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.network.PokeService
import com.example.data.network.PokemonDto
import com.example.data.network.PokemonResponse
import com.example.database.Pokemon
import com.example.database.PokemonDAO
import com.example.database.PokemonDatabase
import com.example.repositories.PokemonRepository
import com.example.repositories.PokemonRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.net.HttpURLConnection

@RunWith(RobolectricTestRunner::class)
class PokemonRepositoryImplTest {

    private lateinit var repository: PokemonRepository
    private lateinit var testService: PokeService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var pokemonDAO: PokemonDAO
    private lateinit var db: PokemonDatabase
    private lateinit var actualResponse: Response<PokemonResponse>

    private val dummyPokemonDtoList = listOf(
        PokemonDto(name = "Electabuzz", url = "electabuzz.com"),
        PokemonDto(name = "Rhydon", url = "rhydon.com"),
        PokemonDto(name = "Lapras", url = "lapras.com"),
        PokemonDto(name = "Arcanine", url = "arcanine.com"),
        PokemonDto(name = "Exeggutor", url = "exeggutor.com"),
        PokemonDto(name = "Aerodactyl", url = "aerodactyl.com"),
    )
    private val dummyPokemonList = listOf(
        Pokemon(name = "Electabuzz", url = "electabuzz.com"),
        Pokemon(name = "Rhydon", url = "rhydon.com"),
        Pokemon(name = "Lapras", url = "lapras.com"),
        Pokemon(name = "Arcanine", url = "arcanine.com"),
        Pokemon(name = "Exeggutor", url = "exeggutor.com"),
        Pokemon(name = "Aerodactyl", url = "aerodactyl.com"),
    )
    private val dummyPokemon = Pokemon(
        id = 0,
        name = "Dragonite",
        url = "www.dragonite.com"
    )

    enum class DummyPokeResponse(val code: Int) {
        HTTP_OK(code = 200),
        HTTP_NOT_FOUND(code = 404),
        HTTP_SERVER_ERROR(code = 500)
    }


    private fun mockService(response: DummyPokeResponse): PokeService {
        return when (response) {
            DummyPokeResponse.HTTP_OK -> {
                object : PokeService {
                    override suspend fun getOriginalPokemon(): Response<PokemonResponse> {
                        return Response.success(
                            DummyPokeResponse.HTTP_OK.code,
                            PokemonResponse(
                                result = dummyPokemonDtoList
                            )
                        )
                    }
                }
            }
            DummyPokeResponse.HTTP_NOT_FOUND -> {
                object : PokeService {
                    override suspend fun getOriginalPokemon(): Response<PokemonResponse> {
                        return Response.error(
                            DummyPokeResponse.HTTP_NOT_FOUND.code,
                            "HTTP 404 Not found".toResponseBody(contentType = null)
                        )
                    }
                }
            }
            DummyPokeResponse.HTTP_SERVER_ERROR -> {
                object : PokeService {
                    override suspend fun getOriginalPokemon(): Response<PokemonResponse> {
                        return Response.error(
                            DummyPokeResponse.HTTP_SERVER_ERROR.code,
                            "HTTP 500 Internal server error".toResponseBody(null)
                        )
                    }
                }
            }
        }
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        testService = mockService(response = DummyPokeResponse.HTTP_OK)

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
    fun http200() {
        testService = mockService(response = DummyPokeResponse.HTTP_OK)

        runBlocking {
            actualResponse = testService.getOriginalPokemon()
        }
        assert(actualResponse.body()?.result?.size == 6)
        assert(actualResponse.code() == HttpURLConnection.HTTP_OK)
    }

    @Test
    @Throws(Exception::class)
    fun http404() {
        testService = mockService(response = DummyPokeResponse.HTTP_NOT_FOUND)

        runBlocking {
            actualResponse = testService.getOriginalPokemon()
        }
        assert(actualResponse.code() == HttpURLConnection.HTTP_NOT_FOUND)
    }

    @Test
    @Throws(Exception::class)
    fun http500() {
        testService = mockService(response = DummyPokeResponse.HTTP_SERVER_ERROR)

        runBlocking {
            actualResponse = testService.getOriginalPokemon()
        }
        assert(actualResponse.code() == HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @Test
    @Throws(Exception::class)
    fun getOriginalPokemonFromNetworkTest(){
        testService = mockService(response = DummyPokeResponse.HTTP_OK)
        repository = PokemonRepositoryImpl(pokemonDAO, testService)
        var pokemonNumber: Int

        runBlocking {
            withContext(Dispatchers.IO){
                pokemonDAO.deleteAllPokemon()
                repository.getOriginalPokemonFromNetwork()
                pokemonNumber = pokemonDAO.pokemonCount()
            }
        }

        assert(pokemonNumber == 6)
    }
 // This test does not work
    @Test
    @Throws(Exception::class)
    fun getAllPokemonTest(){
        testService = mockService(response = DummyPokeResponse.HTTP_OK)
        repository = PokemonRepositoryImpl(pokemonDAO, testService)
        var pokemonList: List<Pokemon>
        runBlocking {
            withContext(Dispatchers.IO){
                pokemonDAO.deleteAllPokemon()
                repository.getOriginalPokemonFromNetwork()
                pokemonList = repository.getAllPokemon().first()
            }
        }
        assert(pokemonList == dummyPokemonList)

    }

    @Test
    @Throws(Exception::class)
    fun getPokemonByIdTest(){
        testService = mockService(response = DummyPokeResponse.HTTP_OK)
        repository = PokemonRepositoryImpl(pokemonDAO, testService)
        var pokemon: Pokemon

        runBlocking {
            withContext(Dispatchers.IO){
                pokemonDAO.deleteAllPokemon()
                repository.getOriginalPokemonFromNetwork()
                pokemon = repository.getPokemonById(id = 1).first()
            }
        }

        assert(pokemon.name == dummyPokemonList[0].name)
    }

    @Test
    @Throws(Exception::class)
    fun getPokemonByNameTest(){
        testService = mockService(response = DummyPokeResponse.HTTP_OK)
        repository = PokemonRepositoryImpl(pokemonDAO, testService)
        var pokemon: Pokemon

        runBlocking {
            withContext(Dispatchers.IO){
                pokemonDAO.deleteAllPokemon()
                repository.getOriginalPokemonFromNetwork()
                pokemon = repository.getPokemonByName(name = "Lapras").first()
            }
        }

        assert(pokemon.url == dummyPokemonList[2].url)
    }

    @Test
    @Throws(Exception::class)
    fun insertPokemonTest(){
        testService = mockService(response = DummyPokeResponse.HTTP_OK)
        repository = PokemonRepositoryImpl(pokemonDAO, testService)
        var pokemon : Pokemon

        runBlocking {
            withContext(Dispatchers.IO){
                pokemonDAO.deleteAllPokemon()
                repository.insertPokemon(dummyPokemon)
                pokemon = repository.getPokemonById(id = 0).first()
            }
        }
        assert(pokemon == dummyPokemon)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllPokemonTest(){
        testService = mockService(response = DummyPokeResponse.HTTP_OK)
        repository = PokemonRepositoryImpl(pokemonDAO, testService)
        var pokemonNumber : Int

        runBlocking {
            withContext(Dispatchers.IO){
                repository.getOriginalPokemonFromNetwork()
                repository.deleteAllPokemon()
                pokemonNumber = pokemonDAO.pokemonCount()
            }
        }
        assert(pokemonNumber == 0)
    }
}