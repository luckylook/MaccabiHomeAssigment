package com.example.maccabi.di

import com.example.maccabi.data.local.ICategoryDAO
import com.example.maccabi.data.local.IProductDAO
import com.example.maccabi.data.remote.CatalogService
import com.example.maccabi.data.repositories.CatalogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    private val dispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideCatalogRepository(service: CatalogService, categoryDAO: ICategoryDAO, productDAO: IProductDAO): CatalogRepository {
        return CatalogRepository(dispatcher, service, categoryDAO, productDAO)
    }
}