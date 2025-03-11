package com.henry.fakestore.modules.favorite.ui.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.databinding.ItemProductFavoriteBinding
import com.henry.fakestore.modules.favorite.ui.adapter.listener.ProductListener
import com.henry.fakestore.modules.utils.GlideExtension

class FavoriteViewHolder (private val binding: ItemProductFavoriteBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product, listener: ProductListener) = with(binding){
        item.apply {
            GlideExtension().setImage(imageProductImageView, image ?: "")
            priceTextView.text = price
            nameProductTextView.text = title
            descriptionTextView.text = description

            ratingConstraintLayout.ratingTextView.text = rating
            ratingConstraintLayout.reviewsTextView.text = count
            deleteProductButton.setOnClickListener {
                listener.onDeleteProduct(item)
            }
        }
    }
}