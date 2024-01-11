package com.stalcraft.blackliststalcraft.di

import com.stalcraft.blackliststalcraft.data.local.MainDb
import com.stalcraft.blackliststalcraft.data.local.repo.PlayerRepoImpl
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import com.stalcraft.blackliststalcraft.domain.usecase.AddUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LogicModule {
    @Provides
    @Singleton
    fun provideCourseRepo(mainDb: MainDb): PlayerRepo {
        return PlayerRepoImpl(mainDb)
    }
    @Provides
    @Singleton
    fun provideAddUseCase(playerRepo: PlayerRepo): AddUseCase {
        return AddUseCase(playerRepo)
    }
}