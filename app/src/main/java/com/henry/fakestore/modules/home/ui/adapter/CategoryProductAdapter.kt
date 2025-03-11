package com.henry.fakestore.modules.home.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.henry.fakestore.R
import com.henry.fakestore.databinding.ItemCategoryBinding
import com.henry.fakestore.modules.contants.Constants
import com.henry.fakestore.modules.home.ui.adapter.listener.CategoryListener

class CategoryProductAdapter(private val listener: CategoryListener): RecyclerView.Adapter<CategoryProductAdapter.CategoryViewHolder>() {

    private var items = listOf<String?>()
    private var selectedPosition = 0


    fun categoriesAdapter(categories: List<String?>){
        items = categories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position == selectedPosition, listener)
    }

    inner class CategoryViewHolder (private val binding: ItemCategoryBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(category: String?, isSelected: Boolean, listener: CategoryListener) = with(binding) {
            binding.categoryButton.text = category

            if (isSelected ) {
                categoryButton.background = binding.root.context.getDrawable(R.drawable.bullet_clicked_button)
                categoryButton.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            } else {
                categoryButton.background = binding.root.context.getDrawable(R.drawable.bullet_button)
                categoryButton.setTextColor(ContextCompat.getColor(binding.root.context, R.color.purple))
            }

            categoryButton.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                listener.onCategoryClicked(category)
            }
        }
    }
}