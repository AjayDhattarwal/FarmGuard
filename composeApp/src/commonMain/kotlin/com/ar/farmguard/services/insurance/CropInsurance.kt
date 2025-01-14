@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.ar.farmguard.services.insurance

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ar.farmguard.core.presentation.navigation.TargetKey
import com.ar.farmguard.core.presentation.navigation.ServiceDestination
import com.ar.farmguard.app.utils.PMFBY_IMG
import com.ar.farmguard.core.presentation.shared.components.TopBar
import com.ar.farmguard.services.common.presentation.components.ServiceCard
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.ic_feedback
import farmguard.composeapp.generated.resources.ic_help
import org.jetbrains.compose.resources.vectorResource

@ExperimentalSharedTransitionApi
@Composable
fun CropInsuranceScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    isLoggedIn: Boolean,
    farmerName: String? = null,
    onBackPress: () -> Unit,
    navigate: (Any) -> Unit,
) {
    val scrollState = rememberScrollState()

    val offset by remember{
        derivedStateOf {
            scrollState.value
        }
    }

    val isFarmerLogin = false

    with(sharedTransitionScope) {

        Scaffold(
            topBar = {
                TopBar(
                    modifier = Modifier,
                    onBackPress = onBackPress
                )
            }
        ) { paddingValues ->


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
            ) {


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PMFBY",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(Modifier.width(5.dp))

                    AsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(PMFBY_IMG)
                            .crossfade(true)
                            .placeholderMemoryCacheKey("image-PMFBY")
                            .memoryCacheKey("image-PMFBY")
                            .build(),
                        contentDescription = "Logo",
                        modifier = Modifier.size(30.dp)
                            .sharedBounds(
                                sharedTransitionScope.rememberSharedContentState(key = "image-PMFBY${if(offset < 50) 0 else 1}"),
                                animatedVisibilityScope = animatedContentScope,
                            )
                    )
                }


                Text(
                    text = "Crop Insurance",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.offset {
                        IntOffset(0, -10)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (isFarmerLogin) {
                    Text(
                        text = if (isLoggedIn) "Welcome, $farmerName" else "Login or Signup",
                        style = MaterialTheme.typography.titleSmall,
                    )

                    Spacer(Modifier.height(10.dp))
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {


                    ServiceCard(
                        title = "Insurance Premium Calculator",
                        description = "Know your insurance premium before",
                        icon = vectorResource(Res.drawable.ic_help),
                        onClick = { navigate(ServiceDestination.InsuranceCalculator) },
                        buttonText = "Calculate"
                    )


                    ServiceCard(
                        title = "Application Status",
                        description = "Know your application status on every step",
                        icon = vectorResource(Res.drawable.ic_feedback),
                        onClick = { navigate(ServiceDestination.ApplicationStatus) },
                        buttonText = "Check Status"
                    )


                    ServiceCard(
                        title = "Farmer Corner",
                        description = "Apply for corp insurance YourSelf",
                        icon = vectorResource(Res.drawable.ic_help),
                        onClick = {
                            if (isLoggedIn) {
                                navigate(ServiceDestination.Login(TargetKey.APPLY_INSURANCE.toString()))
                            } else {
                                navigate(ServiceDestination.ApplyInsurance)
                            }
                        },
                        buttonText = "Farmer Corner",
                    )

                    ServiceCard(
                        title = "Your Applications",
                        description = "View Your Applications",
                        icon = vectorResource(Res.drawable.ic_feedback),
                        onClick = {
                            if (isLoggedIn) {
                                navigate(ServiceDestination.Login(TargetKey.APPLICATIONS.toString()))
                            } else {
                                navigate(ServiceDestination.Applications)
                            }
                        },
                        buttonText = "View Applications"
                    )

                    Spacer(Modifier.height(100.dp))

                }
            }
        }
    }
}


