package com.example.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.example.data.network.retrofit.PokeService
import com.example.data.network.retrofit.PokemonDto
import com.example.data.network.retrofit.PokemonResponse
import com.example.database.Pokemon
import com.example.database.PokemonRegion
import com.example.database.PokemonType
import com.example.datasource.localdatasource.PokemonLocalDataSource
import com.example.datasource.remotedatasource.PokemonRemoteDataSource
import com.example.helpers.Logger
import com.example.repositories.PokemonRepository
import com.example.repositories.PokemonRepositoryImpl
import com.example.tddnetworkpokedex.JohtoPokemonQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class PokemonRepositoryTest {

    private lateinit var repository: PokemonRepository
    private lateinit var mockLocalDataSource: PokemonLocalDataSource
    private lateinit var mockRemoteDataSource: PokemonRemoteDataSource
    private lateinit var logger: Logger
    private lateinit var mockApolloClient: ApolloClient
    private lateinit var pokeService: PokeService

    private val bulbasaur = Pokemon(
        id = 1,
        name = "Bulbasaur",
        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        height = 0.7,
        weight = 6.9,
        types = listOf(PokemonType.GRASS, PokemonType.POISON).toMutableList(),
        sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        stats = listOf(45, 49, 49, 65, 65, 45),
        description = "While it is young, it uses the nutrients that are stored in the seeds on its back in order to grow.",
        region = PokemonRegion.KANTO
    )
    private val ivysaur =  Pokemon(
        id = 2,
        name = "Ivysaur",
        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
        height = 1.0,
        weight = 13.0,
        types = listOf(PokemonType.GRASS, PokemonType.POISON).toMutableList(),
        sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
        stats = listOf(60, 62, 63, 80, 80, 60),
        description = "The bulb on its back grows as it absorbs nutrients. The bulb gives off a pleasant aroma when it blooms.",
        region = PokemonRegion.KANTO
    )

    @OptIn(ApolloExperimental::class)
    @Before
    fun setUp(){
        mockLocalDataSource = mock()
        mockRemoteDataSource = mock()
        logger = mock()
        pokeService = mock()
        repository = PokemonRepositoryImpl(mockLocalDataSource, mockRemoteDataSource, logger)

        mockApolloClient = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
    }

    @Test
    fun `getKantoPokemon returns data from local data source when available`(): Unit = runTest {
        val expectedPokemonList = listOf(bulbasaur, ivysaur)
        whenever(mockLocalDataSource.getKantoPokemon()).thenReturn(flowOf(expectedPokemonList))

        val result = repository.getKantoPokemon().first()

        assertEquals(expectedPokemonList, result)
    }

    @Test
    fun `getKantoPokemon makes network request and updates local data source`() = runTest {
        whenever(mockLocalDataSource.getKantoPokemon()).thenReturn(flowOf(emptyList()))


        val pokemonDtoList = listOf(
            PokemonDto(name = "Bulbasaur", url = "https://example.com/bulbasaur"),
        )
        val pokemonResponse = PokemonResponse(result = pokemonDtoList)

        val expectedRemotePokemonList: Response<PokemonResponse> = Response.success(pokemonResponse)
        whenever(mockRemoteDataSource.getOriginalPokemon()).doReturn(expectedRemotePokemonList)

        repository.getKantoPokemon().first()

        verify(mockLocalDataSource).insertAllPokemon(
            listOf(
                Pokemon(
                    id = 1,
                    name = "Bulbasaur",
                    url = "https://example.com/bulbasaur",
                    height = 0.0,
                    weight = 0.0,
                    types = mutableListOf(PokemonType.UNKNOWN),
                    sprite = "",
                    stats = emptyList(),
                    description = "",
                    region = PokemonRegion.KANTO
                )
            )
        )
    }

    @Test
    fun `getKantoPokemon handles network error`() = runTest {
        whenever(mockLocalDataSource.getKantoPokemon()).thenReturn(flowOf(emptyList()))
        whenever(mockRemoteDataSource.getOriginalPokemon()).thenThrow(RuntimeException("Network error"))

        try {
            repository.getKantoPokemon().collect()
            fail("Expected an exception to be thrown")
        } catch (e: Exception) {
            assertTrue(e is RuntimeException)
            assertEquals("Network error", e.message)
        }

        verify(mockLocalDataSource).getKantoPokemon()
        verify(mockRemoteDataSource).getOriginalPokemon()
        verifyNoMoreInteractions(mockLocalDataSource)
    }

    @Test
    fun `getJohtoPokemon returns data from local data source when available`() = runTest {
        val expectedPokemonList = listOf(bulbasaur, ivysaur)
        whenever(mockLocalDataSource.getJohtoPokemon()).thenReturn(flowOf(expectedPokemonList))

        val result = repository.getJohtoPokemon().first()

        assertEquals(expectedPokemonList, result)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `getJohtoPokemon makes network request and updates local data source`() = runTest {
        val incompletePokemon = listOf(
            Pokemon(
                id = 252,
                name = "Chikorita"
            )
        )
        whenever(mockLocalDataSource.getJohtoPokemon()).thenReturn(flowOf(incompletePokemon))

        val networkPokemon = listOf(
            JohtoPokemonQuery.Gen3Specy(
                id = 252,
                name = "Chikorita",
                attributes = listOf(
                    JohtoPokemonQuery.Attribute(
                        height = 7,
                        weight = 64,
                        stats = listOf(JohtoPokemonQuery.Stat(base_stat = 45)),
                        types = listOf(JohtoPokemonQuery.Type(pokemon_v2_type = JohtoPokemonQuery.Pokemon_v2_type(name = "grass")))
                    )
                ),
                flavorTexts = listOf(
                    JohtoPokemonQuery.FlavorText(
                        flavor_text = "A sweet aroma gently wafts from the leaf on its head.",
                        version = JohtoPokemonQuery.Version(
                            versionNames = listOf(JohtoPokemonQuery.VersionName(name = "Gold"))
                        )
                    )
                )
            )
        )

        val testQuery = JohtoPokemonQuery()
        val testData = JohtoPokemonQuery.Data(networkPokemon)
        mockApolloClient.enqueueTestResponse(testQuery, testData)
        val testResponse = mockApolloClient.query(testQuery).execute()

        whenever(mockRemoteDataSource.getJohtoPokemon()).thenReturn(testResponse)

        val result = repository.getJohtoPokemon().first()

        // Verify the local data source was updated
        verify(mockLocalDataSource).getJohtoPokemon()
        verify(mockLocalDataSource).insertPokemon(any())

        // Assert the result
        assert(result.size == 2)
        assert(result[1].name == "Chikorita")
        assert(result[1].height == 7.0)
    }
//
//    @Test
//    fun `getJohtoPokemon handles network error`() = runTest {
//        whenever(mockLocalDataSource.getJohtoPokemon()).thenReturn(flowOf(emptyList()))
//        whenever(mockRemoteDataSource.getJohtoPokemon()).thenThrow(Exception("Network error"))
//
//        repository.getJohtoPokemon().first()
//
//        verify(logger).e(any(), eq("Network error"))
//    }
//
//    @Test
//    fun `deleteAllPokemon deletes all pokemon from local data source`() {
//        repository.deleteAllPokemon()
//
//        verify(mockLocalDataSource).deleteAllPokemon()
//    }
//
//    @Test
//    fun `getPokemonById returns pokemon from local data source`() = runTest {
//        val expectedPokemon = bulbasaur
//        whenever(mockLocalDataSource.getPokemonById(1)).thenReturn(flowOf(expectedPokemon))
//
//        val result = repository.getPokemonById(1).first()
//
//        assertEquals(expectedPokemon, result)
//    }
//
//    @Test
//    fun `getPokemonByName returns pokemon from local data source`() = runTest {
//        val expectedPokemon = bulbasaur
//        whenever(mockLocalDataSource.getPokemonByName("Bulbasaur")).thenReturn(flowOf(expectedPokemon))
//
//        val result = repository.getPokemonByName("Bulbasaur").first()
//
//        assertEquals(expectedPokemon, result)
//    }
//
//    @Test
//    fun `insertPokemon inserts pokemon into local data source`() = runTest {
//        repository.insertPokemon(bulbasaur)
//
//        verify(mockLocalDataSource).insertPokemon(bulbasaur)
//    }
//
//    @Test
//    fun `getKantoPokemonDetails updates incomplete pokemon details`() = runTest {
//        val incompletePokemon = bulbasaur.copy(height = 0.0, weight = 0.0, sprite = "", stats = emptyList(), description = "")
//        whenever(mockLocalDataSource.getKantoPokemon()).thenReturn(flowOf(listOf(incompletePokemon)))
//
//        val pokemonDetailsResponse = PokemonDetailsResponse(
//            id = 1,
//            name = "Bulbasaur",
//            height = 7,
//            weight = 69,
//            types = listOf(PokemonTypeDto(Type(name = "grass")), PokemonTypeDto(Type(name = "poison"))),
//            sprites = PokemonSpritesDto(OfficialArtwork("https://example.com/bulbasaur.png")),
//            stats = listOf(PokemonStatDto(45), PokemonStatDto(49), PokemonStatDto(49), PokemonStatDto(65), PokemonStatDto(65), PokemonStatDto(45)),
//            abilities = emptyList()
//        )
//        val pokemonDescriptionResponse = PokemonDescriptionResponse(
//            flavorTextEntries = listOf(PokemonFlavorTextEntry("While it is young, it uses the nutrients that are stored in the seeds on its back in order to grow.", Version("crystal")))
//        )
//
//        whenever(mockRemoteDataSource.getPokemonById(1)).thenReturn(Response.success(pokemonDetailsResponse))
//        whenever(mockRemoteDataSource.getPokemonDescription(1)).thenReturn(Response.success(pokemonDescriptionResponse))
//
//        repository.getKantoPokemonDetails()
//
//        verify(mockLocalDataSource).insertPokemon(any())
//    }
}