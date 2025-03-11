package com.henry.fakestore.modules.koinModules

import com.henry.fakestore.data.di.LocalManagerModule
import com.henry.fakestore.data.di.RemoteManagerModule.moduleRetrofit
import com.henry.fakestore.modules.favorite.domain.repository.ProductFavoriteRepository
import com.henry.fakestore.modules.favorite.domain.repository.ProductFavoriteRepositoryImpl
import com.henry.fakestore.modules.home.domain.repository.remoto.ProductHomeRemoteRepository
import com.henry.fakestore.modules.home.domain.repository.remoto.ProductsHomeRemoteRepositoryImpl
import com.henry.fakestore.modules.favorite.domain.usecase.ProductFavoriteUseCase
import com.henry.fakestore.modules.favorite.domain.usecase.ProductFavoriteUseCaseImpl
import com.henry.fakestore.modules.home.domain.usecase.remoto.ProductHomeRemoteUseCase
import com.henry.fakestore.modules.home.domain.usecase.remoto.ProductHomeRemoteUseCaseImpl
import com.henry.fakestore.modules.favorite.ui.FavoriteViewModel
import com.henry.fakestore.modules.home.domain.repository.local.ProductHomeLocalRepository
import com.henry.fakestore.modules.home.domain.repository.local.ProductHomeLocalRepositoryImpl
import com.henry.fakestore.modules.home.domain.usecase.local.ProductHomeLocalUseCase
import com.henry.fakestore.modules.home.domain.usecase.local.ProductHomeLocalUseCaseImpl
import com.henry.fakestore.modules.home.ui.viewModel.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object ApplicationModule {

    private val moduleRepository = module {
        factory<ProductHomeRemoteRepository> {
            ProductsHomeRemoteRepositoryImpl(
                storeApi = get(),
            )
        }
        factory<ProductHomeLocalRepository>{
            ProductHomeLocalRepositoryImpl(
                productDao = get()
            )
        }

        factory<ProductFavoriteRepository> {
            ProductFavoriteRepositoryImpl(
                productDao = get()
            )
        }
    }

    private val moduleUseCase = module {
        factory<ProductHomeRemoteUseCase> {
            ProductHomeRemoteUseCaseImpl(repository = get())
        }
        factory<ProductHomeLocalUseCase> {
            ProductHomeLocalUseCaseImpl(repository = get())
        }

        factory<ProductFavoriteUseCase> {
            ProductFavoriteUseCaseImpl(repository = get())
        }
    }

    private val moduleViewModel = module {
        viewModel{
            HomeViewModel(
                productRemoteUseCase = get(),
                productHomeLocalUseCase = get())
        }
        viewModel {
            FavoriteViewModel(productUseCase = get())
        }
    }

    fun loadModules() {
        loadKoinModules(
            listOf(
                LocalManagerModule.moduleDao(),
                LocalManagerModule.moduleDataBase(),
                moduleRetrofit(),
                moduleRepository,
                moduleUseCase,
                moduleViewModel
            )
        )
    }
}