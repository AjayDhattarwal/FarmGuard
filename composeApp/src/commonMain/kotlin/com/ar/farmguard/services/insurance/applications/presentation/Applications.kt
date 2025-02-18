@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class,)

package com.ar.farmguard.services.insurance.applications.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ar.farmguard.app.utils.PMFBY_IMG
import com.ar.farmguard.core.presentation.shared.components.TopBar
import com.ar.farmguard.services.insurance.applications.presentation.components.PolicyViewCard
import com.ar.farmguard.services.insurance.auth.presentation.signup.components.SelectorField
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun Application(
    viewModel: ApplicationsViewModel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navigate: (Any) -> Unit,
    onBackPress: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()


    var showResult by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopBar(
                onBackPress = onBackPress,
                title = "Application",
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            with(sharedTransitionScope){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(PMFBY_IMG)
                            .crossfade(true)
                            .placeholderMemoryCacheKey("image-PMFBY0")
                            .memoryCacheKey("image-PMFBY0")
                            .build(),
                        contentDescription = "PMFBY Logo",
                        modifier = Modifier.size(150.dp)
                            .sharedBounds(
                                sharedTransitionScope.rememberSharedContentState(key = "image-PMFBY0"),
                                animatedVisibilityScope = animatedContentScope
                            )
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "PMFBY",
                        modifier = Modifier.sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(key = "text-PMFBY"),
                            animatedVisibilityScope = animatedContentScope
                        )
                    )
                    Spacer(Modifier.fillMaxWidth().weight(1f))
                }
            }

            state.message?.let {
                Text(
                    text = it.uiText?.asString()?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = it.color
                )
            }


            SelectorField(
                options = listOf("2024", "2023", "2022", "2021", "2020", "2019", "2018"),
                selectedOption = viewModel.selectedYear,
                onOptionSelected = {
                    viewModel.saveField("year", it)
                },
                placeholder = "Year *",
                readOnly = false
            )

            SelectorField(
                options = listOf("Kharif", "Rabi"),
                selectedOption = viewModel.selectedSeason,
                onOptionSelected = {
                    viewModel.saveField("season", it)
                },
                placeholder = "Season *",
                readOnly = false
            )


            Row (horizontalArrangement = Arrangement.spacedBy(8.dp)){

                Button(
                    onClick = viewModel::reset
                ){
                    Text("Reset")
                }
                Button(
                    onClick = {
                        viewModel.checkPolicy()
                        showResult = true
                    }
                ){
                    Text("Show Policy")
                }
            }

            if(showResult && state.list.isNotEmpty()){
                ModalBottomSheet(
                    onDismissRequest = {
                        showResult = false
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(0.8f),
                ){
                    LazyColumn {
                        items(state.list){
                            PolicyViewCard(it)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }


        }


    }
}