package com.example.dependencyinjection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.data.network.retrofit.PokeService.Companion.pokeService

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
           // .addTypeConverter(PokemonTypeConverter())
            .build()
    }

    @Singleton
    @Provides
    fun providePokeService() = pokeService
}