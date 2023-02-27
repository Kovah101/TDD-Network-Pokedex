package com.example.tddnetworkpokedex


import com.example.data.network.PokemonResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response

class PokeServiceTest {
    private lateinit var response: Response<PokemonResponse>

    @Test
    fun testPokeServiceInstance(){
        val service = com.example.data.network.PokeService.pokeService

        runBlocking {
            response = service.getOriginalPokemon()
        }

        val error = response.errorBody()

        assert(error == null)

        assert(response.isSuccessful)

        assert(response.code() == 200)
    }
}