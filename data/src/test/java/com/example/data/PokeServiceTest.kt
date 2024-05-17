package com.example.data


import com.example.data.network.retrofit.PokeService
import com.example.data.network.retrofit.PokemonDescriptionResponse
import com.example.data.network.retrofit.PokemonDetailsResponse
import com.example.data.network.retrofit.PokemonResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import retrofit2.Response
import retrofit2.Retrofit

class PokeServiceTest {
    private lateinit var pokeService: PokeService
    private lateinit var mockResponse: Response<PokemonResponse>
    private lateinit var mockDetailsResponse: Response<PokemonDetailsResponse>
    private lateinit var mockDescriptionResponse: Response<PokemonDescriptionResponse>

    @Before
    fun setup() {
        pokeService = Mockito.mock(PokeService::class.java)

        mockResponse = Mockito.mock(Response::class.java) as Response<PokemonResponse>
        mockDetailsResponse = Mockito.mock(Response::class.java) as Response<PokemonDetailsResponse>
        mockDescriptionResponse = Mockito.mock(Response::class.java) as Response<PokemonDescriptionResponse>

        runBlocking {
            whenever(pokeService.getOriginalPokemon()).thenReturn(mockResponse)
            whenever(pokeService.getPokemonById(any())).thenReturn(mockDetailsResponse)
            whenever(pokeService.getPokemonDescription(any())).thenReturn(mockDescriptionResponse)
        }
    }

    @Test
    fun `getOriginalPokemon for success`() = runBlocking {
        val response = pokeService.getOriginalPokemon()
        assertEquals(mockResponse, response)
    }

    @Test
    fun `getPokemonById for success`() = runBlocking {
        val response = pokeService.getPokemonById(1)
        assertEquals(mockDetailsResponse, response)
    }

    @Test
    fun `getPokemonDescription for success`() = runBlocking {
        val response = pokeService.getPokemonDescription(1)
        assertEquals(mockDescriptionResponse, response)
    }

    @Test
    fun `getOriginalPokemon returns server error`() = runBlocking {
        val errorResponse = Response.error<PokemonResponse>(500,
            "Server Error".toResponseBody("application/json".toMediaTypeOrNull())
        )
        Mockito.`when`(pokeService.getOriginalPokemon()).thenReturn(errorResponse)

        val response = pokeService.getOriginalPokemon()
        assertEquals(500, response.code())
        assertFalse(response.isSuccessful)
    }

    @Test
    fun `getPokemonById returns server error`() = runBlocking {
        val errorResponse = Response.error<PokemonDetailsResponse>(500,
            "Server Error".toResponseBody("application/json".toMediaTypeOrNull())
        )
        Mockito.`when`(pokeService.getPokemonById(Mockito.anyInt())).thenReturn(errorResponse)

        val response = pokeService.getPokemonById(1)
        assertEquals(500, response.code())
        assertFalse(response.isSuccessful)
    }

    @Test
    fun `getPokemonDescription returns server error`() = runBlocking {
        val errorResponse = Response.error<PokemonDescriptionResponse>(500,
            "Server Error".toResponseBody("application/json".toMediaTypeOrNull())
        )
        Mockito.`when`(pokeService.getPokemonDescription(Mockito.anyInt())).thenReturn(errorResponse)

        val response = pokeService.getPokemonDescription(1)
        assertEquals(500, response.code())
        assertFalse(response.isSuccessful)
    }
}