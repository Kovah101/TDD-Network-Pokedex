package com.example.dependencyinjection

import com.example.helpers.DefaultLogger
import com.example.helpers.Logger
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class HelperModule {

    @Provides
    fun provideLogger(): Logger {
        return DefaultLogger()
    }
}