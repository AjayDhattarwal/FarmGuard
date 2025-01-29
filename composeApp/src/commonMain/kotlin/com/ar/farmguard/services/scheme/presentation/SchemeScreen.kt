@file:OptIn(ExperimentalLayoutApi::class)

package com.ar.farmguard.services.scheme.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import com.ar.farmguard.core.presentation.navigation.ServiceDestination
import com.ar.farmguard.core.presentation.shared.components.ContentCard
import com.ar.farmguard.core.presentation.shared.components.TopBarWithMenu
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SchemeScreen(
    schemeViewModel: SchemeViewModel = koinViewModel(),
    onBackPress: () -> Unit,
    navigate: (Any) -> Unit
){

    val schemeState by schemeViewModel.schemeState.collectAsState()
    val schemeList = schemeViewModel.schemeList.collectAsLazyPagingItems()

    Scaffold{
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                TopBarWithMenu(
                    title = "Schemes",
                    onBackPress = onBackPress,
                    dropDownMenuOptions = listOf("Sort By", "Filter"),
                    onMenuItemSelected = {
                        when (it) {
                            0 -> schemeViewModel.sortBy()
                            1 -> schemeViewModel.filter()
                        }
                    }
                )
            }
            item{
                if(schemeList.loadState.refresh is LoadState.Loading || schemeList.loadState.append is LoadState.Loading){
                    CircularProgressIndicator()
                }
            }
            items(schemeList.itemCount, key = { it }){ index ->

                schemeList.get(index)?.let { scheme ->

                    ContentCard(
                        modifier = Modifier.animateItem(),
                        onClick = {
                            scheme.fields.slug?.let {
                                navigate(
                                    ServiceDestination.SchemeDetails(
                                        it
                                    )
                                )
                            }
                        },
                        elevation = 12.dp,
                        isGradient = false,
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            scheme.fields.schemeName?.let { name ->
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.titleSmall,
                                    maxLines = 1,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = scheme.fields.nodalMinistryName ?: "No Ministry",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(0.4f),
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = scheme.fields.briefDescription ?: "No Description",
                                    style = MaterialTheme.typography.labelMedium,

                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    scheme.fields.tags.forEach {
                                        AssistChip(
                                            onClick = {},
                                            label = { Text(text = it) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item{
                if (schemeState.isRefreshing) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp).size(24.dp)
                        )
                    }
                }
            }

            item("spacerBottom") {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}