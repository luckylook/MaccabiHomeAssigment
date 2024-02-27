package com.example.maccabi.data.domain

import java.util.Currency
import java.util.Locale

data class Product(
    val name: String,

    val image: String,

    val price: Double,

    val quantity: Int,

    ) {

    fun getFormatPrice(): String {
        return if (price % 1.0 == 0.0) {
            String.format("%.0f", price)
        } else {
            String.format("%.2f", price)
        }
    }


    val amountSymbol get() = getCurrencySymbol("ILS", Locale("iw", "IL"))

    private fun getCurrencySymbol(currencyCode: String, locale: Locale = Locale.getDefault()): String {
        val currency = Currency.getInstance(currencyCode)
        return currency.getSymbol(locale)
    }
}
