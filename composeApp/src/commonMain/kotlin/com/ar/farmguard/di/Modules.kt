package com.ar.farmguard.di

import com.ar.farmguard.PlatformImpl
import com.ar.farmguard.app.networking.HttpClientFactory
import com.ar.farmguard.services.insurance.data.network.AuthServiceImpl
import com.ar.farmguard.services.insurance.domain.repository.AuthRepository
import com.ar.farmguard.services.insurance.data.repository.AuthRepositoryImpl
import com.ar.farmguard.services.insurance.presentation.login.LoginViewModel
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.AccountViewModel
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.ResidentialViewModel
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.FarmerViewModel
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.SignUpViewModel
import com.ar.farmguard.app.presentation.theme.PlatformViewModel
import com.ar.farmguard.app.utils.TestViewmodel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    single {
        HttpClientFactory.create(get(), get())
    }

    singleOf(::PlatformImpl)
    singleOf(::AuthServiceImpl)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    viewModel { PlatformViewModel(get()) }
    viewModel { TestViewmodel() }
    viewModel { FarmerViewModel() }
    viewModel { ResidentialViewModel() }
    viewModel { AccountViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { LoginViewModel(get()) }
}
