package com.ar.farmguard.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ar.farmguard.marketprice.presentation.market_home.components.CropCard
import com.ar.farmguard.home.presentation.components.HomeTopBar
import com.ar.farmguard.home.presentation.components.KeyUpdateItem
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.core.presentation.PermissionViewModel
import com.ar.farmguard.news.domian.model.Headline
import com.ar.farmguard.news.presentation.components.BreakingNewsTicker
import com.ar.farmguard.news.presentation.components.NewsSection
import com.ar.farmguard.weather.presentation.components.CurrentWeatherCard
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    navigate: (Any) -> Unit
){

    val homeState by homeViewModel.homeState.collectAsStateWithLifecycle()

    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }

    BindEffect(controller)

    val permissionViewmodel = viewModel {
        PermissionViewModel(controller)
    }

    val permissionState by permissionViewmodel.permissionState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        when(permissionState){
            PermissionState.Granted -> {} //homeViewModel.getWeather()
            PermissionState.DeniedAlways -> {}
            else -> {
                permissionViewmodel.requestPermission()
            }
        }
    }

    val userName by remember{ mutableStateOf("Ajay Singh") }


    Surface {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item(key = "TopSpacer") {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            }

            item(key = "TopHomeBar") {
                HomeTopBar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    userName = userName,
                    profileImageUrl = "https://lh3.googleusercontent.com/a/ACg8ocKzOeHLR6u4PCmEh76OEwRuqybxt7t99S337pJaUKKmu3e3DgA=s576-c-no",
                    onSearchClicked = { },
                    onNotificationClicked = { }
                )
                Spacer(Modifier.height(10.dp))
            }

            item(key = "TodayWeather"){
                ContentTitle(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = "Today's Weather"
                ){
                    Box(contentAlignment = Alignment.Center){

                        CurrentWeatherCard(
                            currentWeather = homeState.currentWeather,
                            onClick = {},
                            astro = homeState.astro,
                            isBlurEffect = homeState.isWeatherLoading
                        )

                        if(homeState.isWeatherLoading){
                            CircularProgressIndicator()
                        }
                    }
                }
            }

//            item(key = "keyUpdates") {
//                ContentTitle(
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    title = "Key Updates"
//                ) {
//                    newsList.forEach {
//                        KeyUpdateItem(it) {}
//                    }
//
//                }
//            }


            item(key = "MandiBhav") {
                ContentTitle(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .animateItem(),
                    title = "Mandi Bhav Updates"
                ) {
                    CropCard(
                        imageUrl = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjbmEswGo92XLT45FPzdIeqTy95f-GsO1jQFCsUjsaV5V-H_Gm2rV8zaEGJ7alEFRzpTMf7hCkV67oh2q4SkTCf2emwGx4kQBzukX-PfFwZ9XC1F-tOHJD1rvqGxlXHjPbari8a-TA71eLqCgkJhKdNxHoKNgwgAzqpsjJkrsNDmZUjLjJQoORJcLgC0E4/s200/PlaygroundImage4.heic",
                        cropName = "Wheat",
                        price = "₹100 / QTL",
                        marketName = "Market Name",
                        isPinned = true,
                        priceColor = Color(0xFF1E8805),
                        priceThread = "+₹20 (+13.33%)"
                    )
                }
            }

            item(key = "BreakingNews") {
                if(homeState.breakingNewsList.isNotEmpty()){
                    ContentTitle(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .animateItem(),
                        title = "Breaking News"
                    ){
                        BreakingNewsTicker(
                            newsList = homeState.breakingNewsList,
                            modifier = Modifier
                        )
                    }
                }
            }

            item(key = "Top 10 HeadLines") {
                if(homeState.headlines.isNotEmpty()){
                    ContentTitle(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .animateItem(),
                        title = "Top 10 HeadLines"
                    )
                }
            }

            items(homeState.headlines, key = {it.id}){ headline ->
                KeyUpdateItem(
                    newsTitle = headline.title,
                    image = headline.image,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .animateItem(),
                    onNewsClick = {}
                )
            }



            item(key = "StateNews") {
                NewsSection(
                    newsItems = homeState.stateNews,
                    title = "State News",
                    scaleImg = 1.2f,
                    actionText = "See All",
                    onActionClick = { TODO() },
                    onItemClick = navigate,
                    modifier = Modifier.animateItem()
                )
            }

            item(key = "BottomSpacer"){ Spacer(Modifier.height(120.dp)) }
        }
    }
}

