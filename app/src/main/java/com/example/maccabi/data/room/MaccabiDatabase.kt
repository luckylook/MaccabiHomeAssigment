package com.example.maccabi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.maccabi.data.entity.CategoryEntity
import com.example.maccabi.data.entity.ProductEntity

@Database(
    version = 1,
    entities = [CategoryEntity::class,
        ProductEntity::class
    ],
    exportSchema = false
)
abstract class MaccabiDatabase : RoomDatabase() {

    abstract fun getCategoryDAO(): CategoryDAO

    abstract fun getProductDAO(): ProductDAO
}