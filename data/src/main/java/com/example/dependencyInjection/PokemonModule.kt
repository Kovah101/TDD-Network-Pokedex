package com.example.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.data.network.PokeService
import com.example.database.PokemonDAO
import com.example.database.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PokemonModule {

    @Provides
    fun providePokemonDao(pokemonDatabase: com.example.database.PokemonDatabase): com.example.database.PokemonDAO {
        return pokemonDatabase.pokemonDao()
    }

    @Provides
    fun providePokemonDatabase(@ApplicationContext appContext: Context): com.example.database.PokemonDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            com.example.database.PokemonDatabase::class.java,
            "pokemon_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePokeService() = com.example.data.network.PokeService.pokeService
}