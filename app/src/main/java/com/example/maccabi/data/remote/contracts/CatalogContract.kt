package com.example.maccabi.data.remote.contracts

import com.google.gson.annotations.SerializedName

data class CatalogContract(
    @SerializedName(value = "products")
    val productContracts: List<ProductContract>
)