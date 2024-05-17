package com.example.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import com.example.tddnetworkpokedex.JohtoPokemonQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ApolloServiceTest {

    @Test
    fun testGetJohtoPokemon() = runBlocking {
        val mockServer = MockServer()
        val apolloClient = ApolloClient.Builder().serverUrl(mockServer.url()).build()

        mockServer.enqueue(
            MockResponse.Builder().statusCode(200).headers(emptyMap()).body(
                body = """
                            {
                              "data": {
                                "gen3Species": [
                                  {
                                    "name": "Chikorita",
                                    "id": "1",
                                    "attributes": [
                                      {
                                        "height": 9,
                                        "weight": 64,
                                        "stats": [
                                          {
                                            "base_stat": 45
                                          }
                                        ],
                                        "types": [
                                          {
                                            "pokemon_v2_type": {
                                              "name": "Grass"
                                            }
                                          }
                                        ]
                                      }
                                    ],
                                    "flavorTexts": [
                                      {
                                        "flavor_text": "A sweet aroma gently wafts from the leaf on its head.",
                                        "version": {
                                          "versionNames": [
                                            {
                                              "name": "Gold"
                                            }
                                          ]
                                        }
                                      }
                                    ]
                                  }
                                ]
                              }
                            }
                        """.trimIndent()
            ).delayMillis(0).build()
        )

        val response = apolloClient.query(JohtoPokemonQuery()).execute()

        val pokemonList = response.data?.gen3Species
        assertEquals(1, pokemonList?.size)
        assertEquals(1, pokemonList?.get(0)?.id)
        assertEquals("Chikorita", pokemonList?.get(0)?.name)

        mockServer.stop()
    }

    @Test
    fun testGetJohtoPokemon_badResponse() = runBlocking {
        val mockServer = MockServer()
        val apolloClient = ApolloClient.Builder().serverUrl(mockServer.url()).build()

        mockServer.enqueue(
            MockResponse.Builder().statusCode(500).headers(emptyMap()).body(
                body = """
                {
                  "errors": [
                    {
                      "message": "Internal server error"
                    }
                  ]
                }
            """.trimIndent()
            ).delayMillis(0).build()
        )

        try {
            val response = apolloClient.query(JohtoPokemonQuery()).execute()
            fail("Expected ApolloException due to bad response")
        } catch (e: ApolloException) {
            // Expected exception due to bad response
        }

        mockServer.stop()
    }
}