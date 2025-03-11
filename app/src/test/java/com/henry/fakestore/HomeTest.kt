package com.henry.fakestore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.modules.home.domain.model.ProductsResult
import com.henry.fakestore.modules.home.domain.model.ProductsResultItem
import com.henry.fakestore.modules.home.domain.model.Rating

import com.henry.fakestore.modules.home.domain.usecase.local.ProductHomeLocalUseCase
import com.henry.fakestore.modules.home.domain.usecase.remoto.ProductHomeRemoteUseCase

import com.henry.fakestore.modules.home.ui.viewModel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class HomeTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: HomeViewModel
    private lateinit var productHomeRemoteUseCase: ProductHomeRemoteUseCase
    private lateinit var productHomeLocalUseCase: ProductHomeLocalUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        productHomeRemoteUseCase = mock(ProductHomeRemoteUseCase::class.java)
        productHomeLocalUseCase = mock(ProductHomeLocalUseCase::class.java)

        viewModel = HomeViewModel(
            productRemoteUseCase = productHomeRemoteUseCase,
            productHomeLocalUseCase = productHomeLocalUseCase,
            dispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `deve filtrar produtos quando estiver uma categoria`() {

        //Given
        val products = mockListProducts()

        val observer = mock(Observer::class.java) as Observer<List<Product>?>
        viewModel.filterProducts.observeForever(observer)

        //When
        viewModel.categoryClick("category", products)

        //Then
        verify(observer).onChanged(listOf(
            Product(
                category = "category",
                description = "description",
                id = 1,
                image = "image",
                price = "10.0",
                rating = "10.0",
                count = "100",
                title = "title"
            )
        ))
        assertEquals("category", viewModel.filterProducts.value?.get(0)?.category )
    }

    @Test
    fun `deve retornar produtos da api`() = runBlocking {
        //Given
        val productsResult = mockListProductsResult()

        //When
        Mockito.`when`(productHomeRemoteUseCase.getProductsRemote()).thenReturn(productsResult)
        viewModel.getProductsRemote()

        //Then
        val responseObserver = Mockito.mock(Observer::class.java) as Observer<List<Product>>
        viewModel.responseProducts.observeForever(responseObserver)

        verify(responseObserver).onChanged(mockListProducts())
    }

    @Test
    fun `deve retornar produtos do banco de dados`() = runBlocking{
        // Given: Preparando os dados simulados que o UseCase vai retornar
        val productsLocal = mockListProducts()
        Mockito.`when`(productHomeLocalUseCase.getProductsLocal()).thenReturn(productsLocal)

        // When: Chama o método para pegar os produtos locais
        viewModel.getProductsLocal()

        // Then: Verifica se o método do UseCase foi chamado
        verify(productHomeLocalUseCase).getProductsLocal()
        assertEquals(productsLocal, viewModel.productsLocal)
    }

    @Test
    fun `deve salvar quando tiver um produto`(): Unit = runBlocking {
        //Given
        val product = Product(
            category = "category",
            description = "description",
            id = 1,
            image = "image",
            price = "10.0",
            rating = "10.0",
            count = "100",
            title = "title"
        )

        // When: Chama o método para salvar o produto
        viewModel.saveProduct(product)

        // Then: Verifica se o método saveProduct foi chamado no mock do UseCase
        verify(productHomeLocalUseCase).saveProduct(product)
    }

    private fun mockListProductsResult(): ProductsResult {
        val productsResult = ProductsResult()
        val productsResultItem = listOf(
            ProductsResultItem(
                category = "category",
                description = "description",
                id = 1,
                image = "image",
                price = 10.0,
                rating = Rating(
                    count = 100,
                    rate = 10.0
                ),
                title = "title"
            ),
            ProductsResultItem(
                category = "category1",
                description = "description2",
                id = 1,
                image = "image3",
                price = 10.0,
                rating = Rating(
                    count = 100,
                    rate = 10.0
                ),
                title = "title4"
            ),
        )
        productsResult.addAll(productsResultItem)
        return productsResult
    }

    private fun mockListProducts(): List<Product> {
        return listOf(
            Product(
                category = "category",
                description = "description",
                id = 1,
                image = "image",
                price = "10.0",
                rating = "10.0",
                count = "100",
                title = "title"
            ),
            Product(
                category = "category1",
                description = "description",
                id = 1,
                image = "image",
                price = "10.0",
                rating = "10.0",
                count = "100",
                title = "title"
            )
        )
    }
}