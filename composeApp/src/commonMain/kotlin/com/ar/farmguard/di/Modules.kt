package com.ar.farmguard.di

import com.ar.farmguard.PlatformImpl
import com.ar.farmguard.app.networking.HttpClientFactory
import com.ar.farmguard.services.insurance.auth.data.network.AuthService
import com.ar.farmguard.services.insurance.auth.domain.repository.AuthRepository
import com.ar.farmguard.services.insurance.auth.data.repository.AuthRepositoryImpl
import com.ar.farmguard.services.insurance.auth.login.LoginViewModel
import com.ar.farmguard.services.insurance.auth.signup.viewmodel.AccountViewModel
import com.ar.farmguard.services.insurance.auth.signup.viewmodel.ResidentialViewModel
import com.ar.farmguard.services.insurance.auth.signup.viewmodel.FarmerViewModel
import com.ar.farmguard.services.insurance.auth.signup.viewmodel.SignUpViewModel
import com.ar.farmguard.app.presentation.theme.PlatformViewModel
import com.ar.farmguard.app.utils.TestViewmodel
import com.ar.farmguard.marketprice.data.network.EnamMandiApiImpl
import com.ar.farmguard.marketprice.data.repository.EnamMandiRepositoryImpl
import com.ar.farmguard.marketprice.domain.network.EnamMandiApi
import com.ar.farmguard.marketprice.domain.repository.EnamMandiRepository
import com.ar.farmguard.marketprice.presentation.MarketPriceViewModel
import com.ar.farmguard.services.insurance.calculator.data.network.PremiumCalculatorApi
import com.ar.farmguard.services.insurance.calculator.data.repository.PremiumCalculatorRepositoryImpl
import com.ar.farmguard.services.insurance.calculator.domain.repository.PremiumCalculatorRepository
import com.ar.farmguard.services.insurance.calculator.presentation.PremiumCalculatorViewModel
import com.ar.farmguard.services.insurance.status.data.network.ApplicationStatusApiImpl
import com.ar.farmguard.services.insurance.status.data.repository.ApplicationStatusRepositoryImpl
import com.ar.farmguard.services.insurance.status.domain.network.ApplicationStatusApi
import com.ar.farmguard.services.insurance.status.domain.repository.ApplicationStatusRepository
import com.ar.farmguard.services.insurance.status.presentation.ApplicationStatusViewModel
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

    singleOf(::AuthService)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    singleOf(::PremiumCalculatorApi)
    singleOf(::PremiumCalculatorRepositoryImpl).bind<PremiumCalculatorRepository>()

    singleOf(::ApplicationStatusRepositoryImpl).bind<ApplicationStatusRepository>()
    singleOf(::ApplicationStatusApiImpl).bind<ApplicationStatusApi>()

    singleOf(::EnamMandiApiImpl).bind<EnamMandiApi>()
    singleOf(::EnamMandiRepositoryImpl).bind<EnamMandiRepository>()


    viewModel { PlatformViewModel(get()) }
    viewModel { TestViewmodel(get()) }
    viewModel { FarmerViewModel(get()) }
    viewModel { ResidentialViewModel() }
    viewModel { AccountViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { PremiumCalculatorViewModel(get()) }
    viewModel { ApplicationStatusViewModel(get()) }
    viewModel { MarketPriceViewModel(get()) }
}
