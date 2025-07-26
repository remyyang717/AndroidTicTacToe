package com.example.compoststudio.di

import com.example.compoststudio.data.repository.local.game_state.LocalGameStateRepository
import com.example.compoststudio.data.repository.local.game_state.LocalGameStateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalGameStateRepository(
        impl: LocalGameStateRepositoryImpl
    ): LocalGameStateRepository {
        return impl
    }
}