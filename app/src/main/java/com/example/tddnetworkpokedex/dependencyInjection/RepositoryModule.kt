package com.example.tddnetworkpokedex.dependencyInjection

import com.example.tddnetworkpokedex.repositories.PokemonRepository
import com.example.tddnetworkpokedex.repositories.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository
}