package com.example.maccabi.di

import android.content.Context
import androidx.room.Room
import com.example.maccabi.data.local.ICategoryDAO
import com.example.maccabi.data.local.IProductDAO
import com.example.maccabi.data.room.MaccabiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DAOModule {

    private const val DB_NAME = "maccabi_db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MaccabiDatabase {
        return Room.databaseBuilder(appContext, MaccabiDatabase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCategoryDAO(database: MaccabiDatabase): ICategoryDAO {
        return database.getCategoryDAO()
    }

    @Provides
    fun provideProductDAO(database: MaccabiDatabase): IProductDAO {
        return database.getProductDAO()
    }
}