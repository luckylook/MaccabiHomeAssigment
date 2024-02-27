package com.example.maccabi.data.domain

data class CatalogState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val categories: List<Category> = mutableListOf()
)
