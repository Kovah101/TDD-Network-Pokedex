package com.example.dependencyinjection

import com.example.datasource.localdatasource.PokemonLocalDataSource
import com.example.datasource.localdatasource.PokemonLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindPokemonLocalDataSource(
        pokemonLocalDataSourceImpl: PokemonLocalDataSourceImpl
    ): PokemonLocalDataSource
}