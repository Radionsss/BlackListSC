package com.stalcraft.blackliststalcraft.core

import android.content.Context
import androidx.room.Room
import com.stalcraft.blackliststalcraft.data.local.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideMainDb(@ApplicationContext context: Context): MainDb =
        Room.databaseBuilder(context, MainDb::class.java,"blackliststalcraft.db").build()
}
