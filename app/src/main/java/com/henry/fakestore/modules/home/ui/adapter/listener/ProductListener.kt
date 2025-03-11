package com.henry.fakestore.modules.home.ui.adapter.listener

import com.henry.fakestore.data.local.model.Product

interface ProductListener {
    fun onFavoriteClicked(product: Product)
    fun onDetailClicked(product: Product)
    fun onOpenFavorites()
}