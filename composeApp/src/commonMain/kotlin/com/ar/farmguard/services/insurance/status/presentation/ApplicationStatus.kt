package com.ar.farmguard.services.insurance.status.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ar.farmguard.core.presentation.shared.components.SvgImage
import com.ar.farmguard.core.presentation.shared.components.TopBar
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import com.ar.farmguard.services.insurance.auth.signup.components.LabeledTextField
import com.ar.farmguard.services.insurance.status.presentation.components.ApplicationStatusCard
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationStatus(
    navigate: (Any) -> Unit,
    onBackPress: () -> Unit,
    viewModel: ApplicationStatusViewModel = koinViewModel(),
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()

    val svgData by viewModel.captchaData.collectAsState()

    val message by viewModel.message.collectAsState()



    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                onBackPress = onBackPress,
                title = "Application Status",
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
            LabeledTextField(
                value = state.policyId,
                onValueChange = { newValue ->
                    if (newValue.text.length < 20) {
                        viewModel.saveField("policyId", newValue)
                    } else {
                        viewModel.saveField("policyId", state.policyId.copy(
                            selection = TextRange(state.policyId.text.length)
                        ))
                    }
                },
                keyboardType = KeyboardType.Number,
                label = "policyId *",
                isError = message?.key == MessageKey.POLICY_STATUS_INFO && message?.status == MessageStatus.ERROR
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                SvgImage(
                    modifier = Modifier.height(60.dp).weight(1f),
                    svgData = svgData,
                    contentDescription = "Captcha"
                )

                IconButton(
                    onClick = viewModel::getCaptcha
                ){
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }

            LabeledTextField(
                value = state.captcha,
                onValueChange = {
                    viewModel.saveField("captcha", it)
                },
                keyboardType = KeyboardType.Text,
                label = "Captcha *",
                isError = message?.key == MessageKey.CAPTCHA_INFO && message?.status == MessageStatus.ERROR
            )

            Row (horizontalArrangement = Arrangement.spacedBy(8.dp)){

                Button(
                    onClick = viewModel::reset
                ){
                    Text("Reset")
                }
                Button(
                    onClick = {
                        viewModel.checkPolicyStatus()
                        showResult = true
                    }
                ){
                    Text("Check Status")
                }
            }

            if(showResult && state.data.isNotEmpty()){
                ModalBottomSheet(
                    onDismissRequest = {
                        showResult = false
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(0.8f),
                ){
                    LazyColumn {
                        items(state.data){
                            ApplicationStatusCard(it)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }


        }


    }

}

