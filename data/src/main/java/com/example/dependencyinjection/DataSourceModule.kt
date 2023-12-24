package com.example.dependencyinjection

import com.example.datasource.localdatasource.PokemonLocalDataSource
import com.example.datasource.localdatasource.PokemonLocalDataSourceImpl
import com.example.datasource.remotedatasource.PokemonRemoteDataSource
import com.example.datasource.remotedatasource.PokemonRemoteDataSourceImpl
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

    @Binds
    abstract fun bindPokemonRemoteDataSource(
        pokemonRemoteDataSourceImpl: PokemonRemoteDataSourceImpl
    ): PokemonRemoteDataSource
}