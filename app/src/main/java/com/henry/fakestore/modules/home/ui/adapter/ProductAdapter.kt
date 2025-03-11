package com.henry.fakestore.modules.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.databinding.ItemProductBinding
import com.henry.fakestore.modules.home.ui.adapter.listener.ProductListener
import com.henry.fakestore.modules.utils.GlideExtension
import com.henry.fakestore.modules.utils.isVisibility

class ProductAdapter(private val listener: ProductListener) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var items = listOf<Product>()

    fun productsAdapter(products: List<Product>) {
        items = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            listener.onDetailClicked(item)
        }
        holder.bind(item, listener, position)
    }
    override fun getItemCount(): Int = items.size

    inner class ProductViewHolder (private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product, listener: ProductListener, position: Int) = with(binding){
            item.apply {
                GlideExtension().setImage(imageProductImageView, image ?: "")
                priceTextView.text = price

                ratingConstraintLayout.ratingTextView.text = rating
                ratingConstraintLayout.reviewsTextView.text = count

                iconFavoriteAppCompatImageButton.isVisibility(isFavorite)
                saveFavoriteButton.isVisibility(!isFavorite)

                saveFavoriteButton.setOnClickListener {
                    item.isFavorite = true
                    listener.onFavoriteClicked(item)
                    saveFavoriteButton.isVisibility(false)
                    notifyItemChanged(position)
                }

                iconFavoriteAppCompatImageButton.setOnClickListener{
                    listener.onOpenFavorites()
                }
            }
        }
    }
}