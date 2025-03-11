package com.henry.fakestore.modules.favorite.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.databinding.ItemProductFavoriteBinding
import com.henry.fakestore.modules.favorite.ui.adapter.viewHolder.FavoriteViewHolder
import com.henry.fakestore.modules.favorite.ui.adapter.listener.ProductListener

class FavoriteAdapter(private val listener: ProductListener) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var items = listOf<Product>()

    fun productsAdapter(products: List<Product>) {
        items = products
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemProductFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            listener.onDetailClicked(item)
        }
        holder.bind(item, listener)
    }
    override fun getItemCount(): Int = items.size
}