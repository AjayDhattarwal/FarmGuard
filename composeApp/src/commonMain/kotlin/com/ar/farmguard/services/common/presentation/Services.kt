@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.ar.farmguard.services.common.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ar.farmguard.core.presentation.navigation.ServiceDestination
import com.ar.farmguard.core.presentation.navigation.SubGraph
import com.ar.farmguard.services.common.presentation.components.ServiceCard
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.core.presentation.shared.components.IconThemeButton
import com.ar.farmguard.core.presentation.shared.components.VerticalGridLayout
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.ic_feedback
import farmguard.composeapp.generated.resources.ic_help
import farmguard.composeapp.generated.resources.ic_insurance_filled
import org.jetbrains.compose.resources.vectorResource

@Composable
fun Services(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navigate: (Any) -> Unit
) {

    val scrollState = rememberScrollState()

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize().verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            ContentTitle(
                title = "Service's",
                isAsTopBar = true,
                icon = {
                    IconThemeButton(
                        icon = Icons.Default.Settings,
                        contentDescription = "Settings",
                        onClick = { navigate(SubGraph.Settings) }
                    )
                }
            )

            ServiceCard(
                title = "Insurance",
                description = "Calculator, Applications, Status",
                icon = vectorResource(Res.drawable.ic_insurance_filled),
                onClick = { navigate(ServiceDestination.Insurance) }
            )

            ServiceCard(
                title = "Schemes",
                description = "Explore Government Schemes",
                icon = Icons.Filled.Info,
                onClick = { navigate(ServiceDestination.Schemes) }
            )

            VerticalGridLayout(
                columns = 2,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                item {
                    ServiceCard(
                        title = "Help",
                        description = "Get Assistance",
                        icon = vectorResource(Res.drawable.ic_help),
                        onClick = { navigate(ServiceDestination.Help) }
                    )
                }
                item {
                    ServiceCard(
                        title = "Feedback",
                        description = "Share Your Feedback",
                        icon = vectorResource(Res.drawable.ic_feedback),
                        onClick = { navigate(ServiceDestination.Feedback) }
                    )
                }

            }

            Spacer(Modifier.height(120.dp))
        }
    }

}

