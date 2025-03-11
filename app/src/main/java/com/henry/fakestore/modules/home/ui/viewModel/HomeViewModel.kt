package com.henry.fakestore.modules.home.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.modules.home.domain.usecase.remoto.ProductHomeRemoteUseCase
import com.henry.fakestore.modules.contants.Constants
import com.henry.fakestore.modules.home.domain.model.ProductsResult
import com.henry.fakestore.modules.home.domain.usecase.local.ProductHomeLocalUseCase
import com.henry.fakestore.modules.utils.FormatedCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val productRemoteUseCase: ProductHomeRemoteUseCase,
    private val productHomeLocalUseCase: ProductHomeLocalUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.Main + SupervisorJob()
) : ViewModel() {

    private val _responseProducts = MutableLiveData<List<Product>>()
    val responseProducts: LiveData<List<Product>> get() = _responseProducts

    private val _filterProducts = MutableLiveData<List<Product>>()
    val filterProducts: LiveData<List<Product>> get() = _filterProducts

    private val _responseCategories = MutableLiveData<List<String>>()
    val responseCategories: LiveData<List<String>> get() = _responseCategories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var  productsLocal = emptyList<Product>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getProductsRemote() {
        _isLoading.value = true
        viewModelScope.launch(dispatcher) {
            val response = productRemoteUseCase.getProductsRemote()
            if(response.isNotEmpty()){
                convertProducts(response)
            }else{
                _error.value = Constants.ERROR
            }
        }
    }

    fun getProductsLocal() {
        viewModelScope.launch(dispatcher) {
            productsLocal = productHomeLocalUseCase.getProductsLocal() ?: emptyList()
            _isLoading.value = false
        }
    }

    fun saveProduct(product: Product) {
        viewModelScope.launch(dispatcher) {
            productHomeLocalUseCase.saveProduct(product)
        }
    }

    fun categoryClick(category: String?, products: List<Product>?) {
        val items = products ?: emptyList()
        if (category == Constants.ALL_CATEGORY) {
            _filterProducts.value = items
            return
        }
        val filterProducts = items.filter { it.category == category }
        val distinctProducts = filterProducts.distinct()
        _filterProducts.value = distinctProducts
    }

    private fun convertProducts(data: ProductsResult) {
        val products = data.map{
            Product(
                id = it.id,
                title = it.title,
                price = FormatedCurrency().toBrazilianCurrency(it.price ?: 0.0),
                description = it.description,
                category = it.category,
                image = it.image,
                rating = it.rating?.rate.toString(),
                count = "(${it.rating?.count})",
            )
        }
        val favoriteProductsFromLocal = favoriteProductsFromLocal(products)

        presenterCategories(favoriteProductsFromLocal)
        presenterProducts(favoriteProductsFromLocal)
        _isLoading.value = false
    }

    private fun favoriteProductsFromLocal(products: List<Product>): List<Product> {
        viewModelScope.launch(dispatcher) {
            productsLocal.forEach { productLocal ->
                products.find { it.id == productLocal.id }?.isFavorite = true
            }
        }
        return products
    }

    private fun presenterProducts(products: List<Product>?) {
        _responseProducts.value = products
    }

    private fun presenterCategories(products: List<Product>?) {
        val categories = products?.mapNotNull { it.category }?.toMutableList() ?: mutableListOf()
        categories.add(0, Constants.ALL_CATEGORY)
        _responseCategories.value = categories.distinct()
    }
}