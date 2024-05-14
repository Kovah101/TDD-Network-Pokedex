package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.network.apollo.ApolloService
import com.example.data.network.retrofit.OfficialArtworkDto
import com.example.data.network.retrofit.OtherSpritesDto
import com.example.data.network.retrofit.PokeService
import com.example.data.network.retrofit.PokemonAbilityDetailsDto
import com.example.data.network.retrofit.PokemonAbilityDto
import com.example.data.network.retrofit.PokemonDescriptionResponse
import com.example.data.network.retrofit.PokemonDetailsResponse
import com.example.data.network.retrofit.PokemonDto
import com.example.data.network.retrofit.PokemonFlavorTextDto
import com.example.data.network.retrofit.PokemonResponse
import com.example.data.network.retrofit.PokemonSpritesDto
import com.example.data.network.retrofit.PokemonStatDetailsDto
import com.example.data.network.retrofit.PokemonStatDto
import com.example.data.network.retrofit.PokemonTypeDetailsDto
import com.example.data.network.retrofit.PokemonTypeDto
import com.example.data.network.retrofit.PokemonVersionDto
import com.example.database.Pokemon
import com.example.database.PokemonDAO
import com.example.database.PokemonDatabase
import com.example.database.PokemonRegion
import com.example.database.PokemonType
import com.example.datasource.localdatasource.PokemonLocalDataSource
import com.example.datasource.localdatasource.PokemonLocalDataSourceImpl
import com.example.datasource.remotedatasource.PokemonRemoteDataSource
import com.example.datasource.remotedatasource.PokemonRemoteDataSourceImpl
import com.example.helpers.DefaultLogger
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
    private lateinit var testRetrofitService: PokeService
    private lateinit var testApolloService: ApolloService
    private lateinit var pokemonDAO: PokemonDAO
    private lateinit var db: PokemonDatabase
    private lateinit var localDataSource: PokemonLocalDataSource
    private lateinit var remoteDataSource: PokemonRemoteDataSource

    private val dummyPokemonDtoList = listOf(
        PokemonDto(name = "Electabuzz", url = "electabuzz.com"),
        PokemonDto(name = "Rhydon", url = "rhydon.com"),
        PokemonDto(name = "Lapras", url = "lapras.com"),
        PokemonDto(name = "Arcanine", url = "arcanine.com"),
        PokemonDto(name = "Exeggutor", url = "exeggutor.com"),
        PokemonDto(name = "Aerodactyl", url = "aerodactyl.com"),
    )
    private val dummyKantoPokemonList = listOf(
        Pokemon(
            id = 1,
            name = "Electabuzz",
            url = "electabuzz.com",
            height = 4.0,
            weight = 14.5,
            types = listOf(PokemonType.ELECTRIC).toMutableList(),
            region = PokemonRegion.KANTO,
            description = "An electric pokemon",
            stats = listOf(80, 70, 68, 72, 110, 120),
            sprite = "www.electabuzz.com"
        ),
        Pokemon(
            id = 2,
            name = "Rhydon",
            url = "rhydon.com",
            height = 5.0,
            weight = 15.5,
            types = listOf(PokemonType.GROUND, PokemonType.ROCK).toMutableList(),
            region = PokemonRegion.KANTO,
            description = "A ground pokemon",
            stats = listOf(105, 130, 120, 45, 45, 40),
            sprite = "www.rhydon.com"
        ),
        Pokemon(
            id = 3,
            name = "Lapras",
            url = "lapras.com",
            height = 6.0,
            weight = 16.5,
            types = listOf(PokemonType.WATER, PokemonType.ICE).toMutableList(),
            region = PokemonRegion.KANTO,
            description = "A water pokemon",
            stats = listOf(130, 85, 80, 85, 95, 60),
            sprite = "www.lapras.com"
        ),
        Pokemon(
            id = 4,
            name = "Arcanine"
            , url = "arcanine.com",
            height = 7.0,
            weight = 17.5,
            types = listOf(PokemonType.FIRE).toMutableList(),
            region = PokemonRegion.KANTO,
            description = "A fire pokemon",
            stats = listOf(110, 80, 80, 100, 80, 95),
            sprite = "www.arcanine.com"
        ),
        Pokemon(
            id = 5,
            name = "Exeggutor",
            url = "exeggutor.com",
            height = 8.0,
            weight = 18.5,
            types = listOf(PokemonType.GRASS, PokemonType.PSYCHIC).toMutableList(),
            region = PokemonRegion.KANTO,
            description = "A grass pokemon",
            stats = listOf(95, 95, 85, 125, 65, 55),
            sprite = "www.exeggutor.com"
        ),
        Pokemon(
            id = 6,
            name = "Aerodactyl",
            url = "aerodactyl.com",
            height = 9.0,
            weight = 19.5,
            types = listOf(PokemonType.ROCK, PokemonType.FLYING).toMutableList(),
            region = PokemonRegion.KANTO,
            description = "A rock pokemon",
            stats = listOf(105, 65, 130, 60, 75, 130),
            sprite = "www.aerodactyl.com"
            ),
    )

    private val dummyJohtoPokemonList = listOf(
        Pokemon(
            id = 7,
            name = "Pichu",
            url = "pichu.com",
            height = 4.0,
            weight = 14.5,
            types = listOf(PokemonType.ELECTRIC).toMutableList(),
            region = PokemonRegion.JOHTO,
            description = "An electric pokemon",
            stats = listOf(80, 70, 68, 72, 110, 120),
            sprite = "www.pichu.com"
        ),
        Pokemon(
            id = 8,
            name = "Donphan",
            url = "donphan.com",
            height = 5.0,
            weight = 15.5,
            types = listOf(PokemonType.GROUND).toMutableList(),
            region = PokemonRegion.JOHTO,
            description = "A ground pokemon",
            stats = listOf(105, 130, 120, 45, 45, 40),
            sprite = "www.donphan.com"
        ),
        Pokemon(
            id = 9,
            name = "Lanturn",
            url = "lanturn.com",
            height = 6.0,
            weight = 16.5,
            types = listOf(PokemonType.WATER, PokemonType.ELECTRIC).toMutableList(),
            region = PokemonRegion.JOHTO,
            description = "A water pokemon",
            stats = listOf(130, 85, 80, 85, 95, 60),
            sprite = "www.lanturn.com"
        ),
        Pokemon(
            id = 10,
            name = "Houndoom"
            , url = "houndoom.com",
            height = 7.0,
            weight = 17.5,
            types = listOf(PokemonType.FIRE, PokemonType.DARK).toMutableList(),
            region = PokemonRegion.JOHTO,
            description = "A fire pokemon",
            stats = listOf(110, 80, 80, 100, 80, 95),
            sprite = "www.houndoom.com"
        ),
        Pokemon(
            id = 11,
            name = "Meganium",
            url = "meganium.com",
            height = 8.0,
            weight = 18.5,
            types = listOf(PokemonType.GRASS).toMutableList(),
            region = PokemonRegion.JOHTO,
            description = "A grass pokemon",
            stats = listOf(95, 95, 85, 125, 65, 55),
            sprite = "www.meganium.com"
        ),
        Pokemon(
            id = 12,
            name = "Skarmory",
            url = "skarmory.com",
            height = 9.0,
            weight = 19.5,
            types = listOf(PokemonType.STEEL, PokemonType.FLYING).toMutableList(),
            region = PokemonRegion.JOHTO,
            description = "A steel pokemon",
            stats = listOf(105, 65, 130, 60, 75, 130),
            sprite = "www.skarmory.com"
        ),
    )
    private val dummyPokemon = Pokemon(
        id = 0,
        name = "Dragonite",
        url = "www.dragonite.com",
        height = 14.0,
        weight = 43.5,
        types = listOf(PokemonType.DRAGON, PokemonType.FLYING).toMutableList(),
        region = PokemonRegion.KANTO,
        description = "A dragon pokemon",
        stats = listOf(100, 100, 100, 100, 100, 100),
        sprite = "www.dragonite.com"
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

                    override suspend fun getPokemonById(id: Int): Response<PokemonDetailsResponse> {
                        return Response.success(
                            DummyPokeResponse.HTTP_OK.code,
                            PokemonDetailsResponse(
                                id = id,
                                name = dummyPokemonDtoList[id].name,
                                height = 4,
                                weight = 14,
                                types = listOf(PokemonTypeDto(PokemonTypeDetailsDto("electric"))),
                                sprites = PokemonSpritesDto(
                                    other = OtherSpritesDto(
                                        officialArtwork = OfficialArtworkDto(
                                            frontDefault = "www.electabuzz.com"
                                        )
                                    )
                                ),
                                stats = listOf(
                                    PokemonStatDto(80, PokemonStatDetailsDto("hp")),
                                    PokemonStatDto(70, PokemonStatDetailsDto("attack")),
                                    PokemonStatDto(68, PokemonStatDetailsDto("defense")),
                                    PokemonStatDto(72, PokemonStatDetailsDto("special-attack")),
                                    PokemonStatDto(110, PokemonStatDetailsDto("special-defense")),
                                    PokemonStatDto(120, PokemonStatDetailsDto("speed")),
                                ),
                                abilities = listOf(
                                    PokemonAbilityDto(PokemonAbilityDetailsDto("static")),
                                )
                            )
                        )
                    }

                    override suspend fun getPokemonDescription(id: Int): Response<PokemonDescriptionResponse> {
                        return Response.success(
                            DummyPokeResponse.HTTP_OK.code,
                            PokemonDescriptionResponse(
                                flavorTextEntries = listOf(
                                    PokemonFlavorTextDto(
                                        flavorText = "An electric pokemon",
                                        version = PokemonVersionDto("crystal")
                                    )
                                )
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

                    override suspend fun getPokemonById(id: Int): Response<PokemonDetailsResponse> {
                        return Response.error(
                            DummyPokeResponse.HTTP_NOT_FOUND.code,
                            "HTTP 404 Not found".toResponseBody(null)
                        )
                    }

                    override suspend fun getPokemonDescription(id: Int): Response<PokemonDescriptionResponse> {
                        return Response.error(
                            DummyPokeResponse.HTTP_NOT_FOUND.code,
                            "HTTP 404 Not found".toResponseBody(null)
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

                    override suspend fun getPokemonById(id: Int): Response<PokemonDetailsResponse> {
                        return Response.error(
                            DummyPokeResponse.HTTP_SERVER_ERROR.code,
                            "HTTP 500 Internal server error".toResponseBody(null)
                        )
                    }

                    override suspend fun getPokemonDescription(id: Int): Response<PokemonDescriptionResponse> {
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
        testRetrofitService = mockService(response = DummyPokeResponse.HTTP_OK)
        //testApolloService = mock(ApolloService::class.java)
       // TODO testApolloService

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java)
            .build()
        pokemonDAO = db.pokemonDao()
        localDataSource = PokemonLocalDataSourceImpl(pokemonDAO)
        remoteDataSource = PokemonRemoteDataSourceImpl(testRetrofitService, testApolloService)
        repository = PokemonRepositoryImpl(localDataSource, remoteDataSource, DefaultLogger())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun `getKantoPokemonFromNetwork gets pokemon from network and saves them in db`() {
        testRetrofitService = mockService(response = DummyPokeResponse.HTTP_OK)
        var pokemonNumber: Int

        runBlocking {
            withContext(Dispatchers.IO) {
                repository.getKantoPokemon()
                pokemonNumber = pokemonDAO.pokemonCount()
            }
        }

        assert(pokemonNumber == 6)
    }

    @Test
    @Throws(Exception::class)
    fun `getKantoPokemon returns all Kanto pokemon from dao `() {
        var pokemonList: List<Pokemon>
        runBlocking {
            withContext(Dispatchers.IO) {
                repository.getKantoPokemon()
                pokemonList = repository.getKantoPokemon().first()
            }
        }
        assert(pokemonList == dummyKantoPokemonList)
    }

    @Test
    @Throws(Exception::class)
    fun `getPokemonById returns specific Kanto pokemon by id from db`() {
        var pokemon: Pokemon

        runBlocking {
            withContext(Dispatchers.IO) {
                pokemonDAO.insertAllPokemon(dummyKantoPokemonList)
                pokemon = repository.getPokemonById(id = 1).first()
            }
        }

        assert(pokemon.name == dummyKantoPokemonList[0].name)
    }

    @Test
    @Throws(Exception::class)
    fun `getPokemonByName returns specific Kanto pokemon by name from db`() {
        var pokemon: Pokemon

        runBlocking {
            withContext(Dispatchers.IO) {
                pokemon = repository.getPokemonByName(name = "Lapras").first()
            }
        }

        assert(pokemon.url == dummyKantoPokemonList[2].url)
    }

    @Test
    @Throws(Exception::class)
    fun `insertPokemon correctly adds new pokemon to db`() {
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
    fun `deleteAllPokemon clears db`() {
        var pokemonNumber: Int

        runBlocking {
            withContext(Dispatchers.IO) {
                repository.getKantoPokemon()
                repository.deleteAllPokemon()
                pokemonNumber = pokemonDAO.pokemonCount()
            }
        }
        assert(pokemonNumber == 0)
    }
}