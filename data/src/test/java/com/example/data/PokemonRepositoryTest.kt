package com.example.data

import com.example.data.network.retrofit.PokemonDto
import com.example.data.network.retrofit.PokemonResponse
import com.example.database.Pokemon
import com.example.database.PokemonRegion
import com.example.database.PokemonType
import com.example.datasource.localdatasource.PokemonLocalDataSource
import com.example.datasource.remotedatasource.PokemonRemoteDataSource
import com.example.helpers.Logger
import com.example.repositories.PokemonRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class PokemonRepositoryTest {

    private val mockLocalDataSource: PokemonLocalDataSource = mock()
    private val mockRemoteDataSource: PokemonRemoteDataSource = mock()
    private val mockLogger: Logger = mock()
    private val repository = PokemonRepositoryImpl(mockLocalDataSource, mockRemoteDataSource, mockLogger)
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
            // Add more PokemonDto objects as needed
        )
        val pokemonResponse = PokemonResponse(result = pokemonDtoList)

// Wrap the PokemonResponse in a successful Response object
        val expectedRemotePokemonList: Response<PokemonResponse> = Response.success(pokemonResponse)
        whenever(mockRemoteDataSource.getOriginalPokemon()).doReturn(expectedRemotePokemonList)

        repository.getKantoPokemon().first()

        expectedRemotePokemonList.forEach {
            verify(mockLocalDataSource).insertPokemon(it)
        }
    }

    @Test
    fun `getKantoPokemon handles network error`() = runTest {
        whenever(mockLocalDataSource.getKantoPokemon()).thenReturn(flowOf(emptyList()))

        whenever(mockRemoteDataSource.getOriginalPokemon()).thenThrow(Exception("Network error"))

        repository.getKantoPokemon().first()

        // Verify that the method handles the error gracefully (for example, by not crashing)
    }
}