package com.henry.fakestore.modules.home.ui

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.henry.fakestore.R
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.databinding.FragmentHomeBinding
import com.henry.fakestore.modules.contants.Constants
import com.henry.fakestore.modules.baseFragment.BaseFragment
import com.henry.fakestore.modules.home.ui.adapter.CategoryProductAdapter
import com.henry.fakestore.modules.home.ui.adapter.ProductAdapter
import com.henry.fakestore.modules.home.ui.adapter.listener.ProductListener
import com.henry.fakestore.modules.home.ui.adapter.listener.CategoryListener
import com.henry.fakestore.modules.home.ui.viewModel.HomeViewModel
import com.henry.fakestore.modules.utils.isVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ProductListener, CategoryListener
{

    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryProductAdapter
    private var products : List<Product> = emptyList()
    private val viewModel : HomeViewModel by viewModel()

    override fun setupViews() {
        configureProductAdapter()

        observerCategoriesResponse()
        observerProductResponse()
        observerError()
        observerLoading()

        viewModel.getProductsLocal()
        viewModel.getProductsRemote()
    }

    override fun onResume() {
        super.onResume()
        observerProductsFake()
    }

    private fun observerProductsFake() {
        viewModel.filterProducts.observe(this.viewLifecycleOwner) {
            renderProductsRecyclerView(it)
        }
    }

    private fun observerLoading() {
        viewModel.isLoading.observe(this.viewLifecycleOwner) {
            binding.progressBar.isVisibility(it)
            binding.productRecyclerView.isVisibility(!it)
        }
    }

    private fun observerCategoriesResponse() {
        viewModel.responseCategories.observe(this.viewLifecycleOwner) {
            configureCategoryAdapter()
            renderCategoryRecyclerView(it)
        }
    }

    private fun observerProductResponse() {
        viewModel.responseProducts.observe(this.viewLifecycleOwner) {
            products = it
            renderProductsRecyclerView(it)
        }
    }
    private fun observerError() {
        viewModel.error.observe(this.viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun configureCategoryAdapter() = with(binding) {
        categoryAdapter = CategoryProductAdapter(this@HomeFragment )
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun renderCategoryRecyclerView(categories: List<String?>) {
        categoryAdapter.categoriesAdapter(categories)
    }

    private fun configureProductAdapter() = with(binding) {
        productAdapter = ProductAdapter(this@HomeFragment)
        productRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)
        productRecyclerView.adapter = productAdapter
    }

    private fun renderProductsRecyclerView(products : List<Product>) {
        productAdapter.productsAdapter(products)
    }

    override fun onFavoriteClicked(product: Product) {
        viewModel.saveProduct(product)
    }

    override fun onDetailClicked(product: Product) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.PRODUCT, product)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun onCategoryClicked(category: String?) {
        viewModel.categoryClick(category, products)
    }

    override fun onOpenFavorites() {
        findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
    }
}