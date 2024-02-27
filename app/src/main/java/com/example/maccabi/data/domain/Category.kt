package com.example.maccabi.data.domain

data class Category(
    val name: String,

    val thumbnail: String,

    val totalProducts: Int,

    val totalStockProducts: Int,

    val products: List<Product>

) {

}
