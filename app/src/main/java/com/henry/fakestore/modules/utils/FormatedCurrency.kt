package com.henry.fakestore.modules.utils

import java.text.NumberFormat
import java.util.Locale

class FormatedCurrency {
    fun toBrazilianCurrency(price: Double): String {
        val brazilianLocale = Locale("pt", "BR")
        val currencyFormatter = NumberFormat.getCurrencyInstance(brazilianLocale)
        return currencyFormatter.format(price) ?: ""
    }
}