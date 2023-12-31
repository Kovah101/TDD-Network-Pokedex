package com.example.data


import com.example.data.network.retrofit.PokeService
import com.example.data.network.retrofit.PokemonResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response

class PokeServiceTest {
    private lateinit var response: Response<PokemonResponse>

    @Test
    fun testPokeServiceInstance(){
        val service = PokeService.pokeService

        runBlocking {
            response = service.getOriginalPokemon()
        }

        val error = response.errorBody()

        assert(error == null)

        assert(response.isSuccessful)

        assert(response.code() == 200)
    }
}