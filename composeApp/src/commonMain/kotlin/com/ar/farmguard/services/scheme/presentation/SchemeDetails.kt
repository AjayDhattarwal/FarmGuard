package com.ar.farmguard.services.scheme.presentation

import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
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

    val commonEn = remember(state){ state.schemeDetails.pageProps.schemeData.en}

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit){
        viewModel.fetchSchemeDetails(id)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars).padding(top = 10.dp))

            Text(
                text = commonEn.basicDetails.schemeName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.basicMarquee(
                    animationMode = MarqueeAnimationMode.Immediately,
                    repeatDelayMillis = 2000,
                    initialDelayMillis = 2000
                ),
            )

            Text(
                text = "Ministry Of Commerce And Industry",
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(0.7f),
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                commonEn.basicDetails.tags.forEach {
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

            commonEn.schemeContent.detailedDescriptionMd?.let{ text ->
                ContentTitle(
                    title = "Scheme Details",
                ){
                    Text(
                        text = text
                    )
                }
            }


            commonEn.schemeContent.benefitsMd?.let { text ->
                ContentTitle(
                    title = "Benefits",
                ){

                    Text(
                        text = text
                    )
                }
            }

            commonEn.eligibilityCriteria.eligibilityDescriptionMd?.let { text ->
                ContentTitle(
                    title = "Eligibility",
                ){
                    Text(
                        text = text
                    )
                }
            }

            commonEn.schemeContent.exclusions?.let { text ->
                ContentTitle(
                    title = "Exclusions",
                ) {
                    Text(text = text)
                }
            }



            ///// Application Process


            state.schemeDetails.pageProps.docs.data.en.documentsRequiredMd?.let { text ->
                ContentTitle(
                    title = "Documents Required",
                ){
                    Text(
                        text = text
                    )
                }
            }

        }
    }
}


