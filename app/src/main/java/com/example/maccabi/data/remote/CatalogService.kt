package com.example.maccabi.data.remote

import com.example.maccabi.data.remote.contracts.CatalogContract
import retrofit2.http.GET

interface CatalogService {

    @GET("/products?limit=100")
    suspend fun getCatalog(): CatalogContract
}