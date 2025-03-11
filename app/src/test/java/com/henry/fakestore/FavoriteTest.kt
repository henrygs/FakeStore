package com.henry.fakestore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.modules.contants.Constants
import com.henry.fakestore.modules.favorite.domain.usecase.ProductFavoriteUseCase
import com.henry.fakestore.modules.favorite.ui.FavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class FavoriteTest {

    //Configuracoes a serem injetadas no construtor viewModel
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var productUseCase: ProductFavoriteUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        productUseCase = Mockito.mock(ProductFavoriteUseCase::class.java)

        viewModel = FavoriteViewModel(productUseCase, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `quando getProductsFavorite for chamado, deve atualizar responseLocal com os produtos`() = runBlocking {
        val expectedProducts = listOf(
            Product(
                id = 1,
                category = "Product 1",
                image = "abelha",
                price = "1.0",
                title = "abelha",
                description = "abelha",
                rating = "10.0",
                count = "2",
                isFavorite = true
            )
        )

        Mockito.`when`(productUseCase.getProductsFavorite()).thenReturn(expectedProducts)

        val observer = Mockito.mock(Observer::class.java) as Observer<List<Product>?>
        viewModel.responseLocal.observeForever(observer)

        viewModel.getProductsFavorite()

        verify(observer).onChanged(expectedProducts)
    }

    @Test
    fun `quando getProductsFavorite falhar, deve atualizar error com uma mensagem`() = runBlocking {
        Mockito.`when`(productUseCase.getProductsFavorite()).thenReturn(null)

        // Cria um mock de Observer para observar a LiveData error e registra
        val observer = Mockito.mock(Observer::class.java) as Observer<String>
        viewModel.error.observeForever(observer)

        viewModel.getProductsFavorite()

        verify(observer).onChanged(Constants.ERROR)
    }

    @Test
    fun `quando deleteProduct for chamado, deve chamar o deleteProduct do UseCase`(): Unit = runBlocking {
        val product = Product(
            id = 1,
            category = "Product 1",
            image = "abelha",
            price = "1.0",
            title = "abelha",
            description = "abelha",
            rating = "10.0",
            count = "2",
            isFavorite = true
        )

        viewModel.deleteProduct(product)

        verify(productUseCase).deleteProduct(product)
        verify(productUseCase).getProductsFavorite()
    }
}