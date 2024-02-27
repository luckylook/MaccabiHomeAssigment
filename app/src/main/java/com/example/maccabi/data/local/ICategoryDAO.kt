package com.example.maccabi.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maccabi.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface ICategoryDAO {

    @Query("Select * from categoryentity")
    fun getAllCategories(): Flow<List<CategoryEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: CategoryEntity): Long
}