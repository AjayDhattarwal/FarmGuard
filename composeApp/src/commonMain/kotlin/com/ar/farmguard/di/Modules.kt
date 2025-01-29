package com.ar.farmguard.di

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
import com.ar.farmguard.core.domain.PaginationManager
import com.ar.farmguard.home.HomeViewModel
import com.ar.farmguard.marketprice.data.network.EnamMandiApiImpl
import com.ar.farmguard.marketprice.data.repository.EnamMandiRepositoryImpl
import com.ar.farmguard.marketprice.domain.network.EnamMandiApi
import com.ar.farmguard.marketprice.domain.repository.EnamMandiRepository
import com.ar.farmguard.marketprice.presentation.SharedCommodityViewModel
import com.ar.farmguard.marketprice.presentation.market_home.MarketCommodityViewModel
import com.ar.farmguard.services.insurance.calculator.data.network.PremiumCalculatorApi
import com.ar.farmguard.services.insurance.calculator.data.repository.PremiumCalculatorRepositoryImpl
import com.ar.farmguard.services.insurance.calculator.domain.repository.PremiumCalculatorRepository
import com.ar.farmguard.services.insurance.calculator.presentation.PremiumCalculatorViewModel
import com.ar.farmguard.services.insurance.status.data.network.ApplicationStatusApiImpl
import com.ar.farmguard.services.insurance.status.data.repository.ApplicationStatusRepositoryImpl
import com.ar.farmguard.services.insurance.status.domain.network.ApplicationStatusApi
import com.ar.farmguard.services.insurance.status.domain.repository.ApplicationStatusRepository
import com.ar.farmguard.services.insurance.status.presentation.ApplicationStatusViewModel
import com.ar.farmguard.services.scheme.data.network.SchemeApiImpl
import com.ar.farmguard.services.scheme.data.network.SchemeDetailsApiImpl
import com.ar.farmguard.services.scheme.data.repository.SchemeDetailsRepositoryImpl
import com.ar.farmguard.services.scheme.data.repository.SchemeRepositoryImpl
import com.ar.farmguard.services.scheme.domain.network.SchemeApi
import com.ar.farmguard.services.scheme.domain.network.SchemeDetailsApi
import com.ar.farmguard.services.scheme.domain.repository.SchemeDetailsRepository
import com.ar.farmguard.services.scheme.domain.repository.SchemeRepository
import com.ar.farmguard.services.scheme.presentation.SchemeDetailsViewModel
import com.ar.farmguard.services.scheme.presentation.SchemeViewModel
import com.ar.farmguard.weather.data.network.WeatherApiImpl
import com.ar.farmguard.weather.data.repository.WeatherRepositoryImpl
import com.ar.farmguard.weather.domain.network.WeatherApi
import com.ar.farmguard.weather.domain.repository.WeatherRepository
import com.ar.farmguard.weather.presentation.WeatherViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    single {
        HttpClientFactory.create(get(), get())
    }

    single { PaginationManager() }

    singleOf(::AuthService)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    singleOf(::PremiumCalculatorApi)
    singleOf(::PremiumCalculatorRepositoryImpl).bind<PremiumCalculatorRepository>()

    singleOf(::ApplicationStatusRepositoryImpl).bind<ApplicationStatusRepository>()
    singleOf(::ApplicationStatusApiImpl).bind<ApplicationStatusApi>()

    singleOf(::EnamMandiApiImpl).bind<EnamMandiApi>()
    singleOf(::EnamMandiRepositoryImpl).bind<EnamMandiRepository>()

    singleOf(::WeatherApiImpl).bind<WeatherApi>()
    singleOf(::WeatherRepositoryImpl).bind<WeatherRepository>()

    singleOf(::SchemeRepositoryImpl).bind<SchemeRepository>()
    singleOf(::SchemeApiImpl).bind<SchemeApi>()

    singleOf(::SchemeDetailsApiImpl).bind<SchemeDetailsApi>()
    singleOf(::SchemeDetailsRepositoryImpl).bind<SchemeDetailsRepository>()


    viewModelOf(::PlatformViewModel)
    viewModelOf(::FarmerViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ResidentialViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::PremiumCalculatorViewModel)
    viewModelOf(::ApplicationStatusViewModel)
    viewModelOf(::SharedCommodityViewModel)
    viewModelOf(::MarketCommodityViewModel)
    viewModelOf(::WeatherViewModel)
    viewModelOf(::SchemeViewModel)
    viewModelOf(::SchemeDetailsViewModel)

}
