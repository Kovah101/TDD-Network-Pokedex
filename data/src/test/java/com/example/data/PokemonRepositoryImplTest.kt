package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.network.retrofit.PokeService
import com.example.data.network.retrofit.PokemonDto
import com.example.data.network.retrofit.PokemonResponse
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
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PokemonRepositoryImplTest {
    //TODO fix tests

    private lateinit var repository: PokemonRepository
    private lateinit var testService: PokeService
    private lateinit var pokemonDAO: PokemonDAO
    private lateinit var db: PokemonDatabase

    private val dummyPokemonDtoList = listOf(
        PokemonDto(name = "Electabuzz", url = "electabuzz.com"),
        PokemonDto(name = "Rhydon", url = "rhydon.com"),
        PokemonDto(name = "Lapras", url = "lapras.com"),
        PokemonDto(name = "Arcanine", url = "arcanine.com"),
        PokemonDto(name = "Exeggutor", url = "exeggutor.com"),
        PokemonDto(name = "Aerodactyl", url = "aerodactyl.com"),
    )
    private val dummyPokemonList = listOf(
        Pokemon(id = 1, name = "Electabuzz", url = "electabuzz.com"),
        Pokemon(id = 2, name = "Rhydon", url = "rhydon.com"),
        Pokemon(id = 3, name = "Lapras", url = "lapras.com"),
        Pokemon(id = 4, name = "Arcanine", url = "arcanine.com"),
        Pokemon(id = 5, name = "Exeggutor", url = "exeggutor.com"),
        Pokemon(id = 6, name = "Aerodactyl", url = "aerodactyl.com"),
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
    }

    @Test
    @Throws(Exception::class)
    fun `getOriginalPokemonFromNetwork gets pokemon from network and saves them in dao`() {
        testService = mockService(response = DummyPokeResponse.HTTP_OK)
        repository = PokemonRepositoryImpl(pokemonDAO, testService)
        var pokemonNumber: Int

        runBlocking {
            withContext(Dispatchers.IO) {
                repository.getOriginalPokemonFromNetwork()
                pokemonNumber = pokemonDAO.pokemonCount()
            }
        }

        assert(pokemonNumber == 6)
    }

    @Test
    @Throws(Exception::class)
    fun `getAllPokemon returns all pokemon from dao `() {
        var pokemonList: List<Pokemon>
        runBlocking {
            withContext(Dispatchers.IO) {
                repository.getOriginalPokemonFromNetwork()
                pokemonList = repository.getAllPokemon().first()
            }
        }
        assert(pokemonList == dummyPokemonList)
    }

    @Test
    @Throws(Exception::class)
    fun `getPokemonById returns specific pokemon by id from dao`() {
        var pokemon: Pokemon

        runBlocking {
            withContext(Dispatchers.IO) {
                repository.getOriginalPokemonFromNetwork()
                pokemon = repository.getPokemonById(id = 1).first()
            }
        }

        assert(pokemon.name == dummyPokemonList[0].name)
    }

    @Test
    @Throws(Exception::class)
    fun `getPokemonByName returns specific pokemon by name from dao`() {
        var pokemon: Pokemon

        runBlocking {
            withContext(Dispatchers.IO) {
                repository.getOriginalPokemonFromNetwork()
                pokemon = repository.getPokemonByName(name = "Lapras").first()
            }
        }

        assert(pokemon.url == dummyPokemonList[2].url)
    }

    @Test
    @Throws(Exception::class)
    fun `insertPokemon correctly adds new pokemon to dao`() {
        var pokemon: Pokemon

        runBlocking {
            withContext(Dispatchers.IO) {
                repository.insertPokemon(dummyPokemon)
                pokemon = repository.getPokemonById(id = 0).first()
            }
        }
        assert(pokemon == dummyPokemon)
    }

    @Test
    @Throws(Exception::class)
    fun `deleteAllPokemon clears dao`() {
        var pokemonNumber: Int

        runBlocking {
            withContext(Dispatchers.IO) {
                repository.getOriginalPokemonFromNetwork()
                repository.deleteAllPokemon()
                pokemonNumber = pokemonDAO.pokemonCount()
            }
        }
        assert(pokemonNumber == 0)
    }
}