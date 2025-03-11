package com.henry.fakestore.modules.favorite.ui.adapter.listener

import com.henry.fakestore.data.local.model.Product

interface ProductListener {
    fun onDetailClicked(product: Product)
    fun onDeleteProduct(product: Product)
}