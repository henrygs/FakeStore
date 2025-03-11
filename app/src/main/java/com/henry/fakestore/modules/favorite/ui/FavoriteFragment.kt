package com.henry.fakestore.modules.favorite.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.henry.fakestore.R
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.databinding.FragmentFavoriteBinding
import com.henry.fakestore.modules.contants.Constants
import com.henry.fakestore.modules.baseFragment.BaseFragment
import com.henry.fakestore.modules.favorite.ui.adapter.FavoriteAdapter
import com.henry.fakestore.modules.favorite.ui.adapter.listener.ProductListener
import com.henry.fakestore.modules.utils.isVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),
    ProductListener {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun setupViews() {
        observerLoading()
        observerFavoriteResponse()
        viewModel.getProductsFavorite()
        responseProductLocal()
    }

    private fun observerLoading() {
        viewModel.isLoading.observe(this.viewLifecycleOwner) {
            binding.progressBar.isVisibility(it)
        }
    }

    private fun observerFavoriteResponse() {
        viewModel.isFavorite.observe(this.viewLifecycleOwner) {
            it?.let { isVisibility ->
                binding.apply {
                    favoriteRecyclerVIew.isVisibility(!isVisibility)
                    favoriteEmptyText.isVisibility(isVisibility)
                }
            }
        }
    }

    private fun responseProductLocal() {
        viewModel.responseLocal.observe(this.viewLifecycleOwner) {
            renderAdapter(it)
        }
    }

    private fun configureProductAdapter() = with(binding) {
        favoriteAdapter = FavoriteAdapter(this@FavoriteFragment)
        favoriteRecyclerVIew.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        favoriteRecyclerVIew.adapter = favoriteAdapter
    }

    private fun renderAdapter(products: List<Product>?) {
        configureProductAdapter()
        products?.let { favoriteAdapter.productsAdapter(it) }
    }

    override fun onDetailClicked(product: Product) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.PRODUCT, product)
        findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
    }

    override fun onDeleteProduct(product: Product) {
        viewModel.deleteProduct(product)
    }
}