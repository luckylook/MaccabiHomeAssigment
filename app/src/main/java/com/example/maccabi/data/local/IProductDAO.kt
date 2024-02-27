package com.example.maccabi.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maccabi.data.entity.ProductEntity

interface IProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("Select * from productentity where category = :categoryName")
    suspend fun getAllProductsByCategoryName(categoryName: String): List<ProductEntity>
}