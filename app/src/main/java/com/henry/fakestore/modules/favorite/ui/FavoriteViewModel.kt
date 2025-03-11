package com.henry.fakestore.modules.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.modules.contants.Constants
import com.henry.fakestore.modules.favorite.domain.usecase.ProductFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoriteViewModel(
    private val productUseCase: ProductFavoriteUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.Main + SupervisorJob()
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _responseLocal = MutableLiveData<List<Product>?>()
    val responseLocal: LiveData<List<Product>?> get() = _responseLocal

    fun getProductsFavorite() {
        _isLoading.value = true
        viewModelScope.launch(dispatcher) {
            val response = productUseCase.getProductsFavorite()
            if(response?.isEmpty() == false) {
                _responseLocal.value = response
                _isFavorite.value = false
            } else {
                _error.value = Constants.ERROR
                _isFavorite.value = true
            }
            _isLoading.value = false
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(dispatcher) {
            productUseCase.deleteProduct(product)
            getProductsFavorite()
        }
    }
}