package com.ar.farmguard.services.scheme.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.services.scheme.presentation.components.CommonApplicationUI
import com.ar.farmguard.services.scheme.presentation.components.CommonContentUI
import com.ar.farmguard.services.scheme.presentation.components.freqAskQuestionUI
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.ic_help
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SchemeDetails(
    id: String,
    viewModel: SchemeDetailsViewModel = koinViewModel(),
    onBackPress: () -> Unit,
    navigate: (Any) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val commonEn = remember(state){ state.schemeDetails.pageProps?.schemeData?.en}

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        viewModel.fetchSchemeDetails(id)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = state.isLoading
        ){ targetState ->
            if(targetState){
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }else{
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars).padding(top = 10.dp))

                    Text(
                        text = commonEn?.basicDetails?.schemeName?:"",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.basicMarquee(
                            animationMode = MarqueeAnimationMode.Immediately,
                            repeatDelayMillis = 2000,
                            initialDelayMillis = 2000
                        ),
                    )
                    commonEn?.basicDetails?.nodalMinistryName?.label?.let{
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(0.7f),
                        )
                    }


                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        commonEn?.basicDetails?.tags?.forEach {
                            AssistChip(
                                onClick = {},
                                shape = MaterialTheme.shapes.small,
                                label = { Text(text = it) },
                                colors = AssistChipDefaults.assistChipColors(
                                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.7f)
                                )
                            )
                        }
                    }

                    commonEn?.schemeContent?.detailedDescription?.let{
                        ContentTitle(
                            title = "Scheme Details",
                        ){
                            CommonContentUI(it)
                        }
                    }



                    commonEn?.schemeContent?.benefits?.let{
                        ContentTitle(
                            title = "Benefits",
                        ){
                            CommonContentUI(it)
                        }
                    }


                    commonEn?.eligibilityCriteria?.eligibilityDescription?.let {
                        ContentTitle(
                            title = "Eligibility",
                        ){
                            CommonContentUI(it)
                        }
                    }

                    commonEn?.schemeContent?.exclusions?.let {
                        ContentTitle(
                            title = "Exclusions",
                        ){
                            CommonContentUI(it)

                        }
                    }

                    state.schemeDetails.pageProps?.docs?.data?.en?.documentsRequired?.let {
                        ContentTitle(
                            title = "Documents Required",
                        ){
                            CommonContentUI(it)
                        }
                    }

                    commonEn?.applicationProcess?.let {
                        ContentTitle(
                            title = "Documents Required",
                        ){
                            CommonApplicationUI(it)
                        }
                    }

                    state.schemeDetails.pageProps?.faqs?.data?.en.let{
                        ContentTitle(
                            title = "Frequently Asked Questions",
                        ){
                            freqAskQuestionUI(it?.faqs)
                        }
                    }

                    commonEn?.schemeContent?.references?.let{
                        ContentTitle(
                            title = "Sources And References",
                        ){
                            it.forEach {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                                ) {
                                    Text(
                                       text = it.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f),
                                        color = Color(0xFF3366CC)
                                    )
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_help),
                                        contentDescription = "info",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }


                    Spacer(Modifier.height(120.dp))

                }
            }
        }

    }
}


