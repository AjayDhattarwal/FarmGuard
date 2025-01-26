package com.ar.farmguard.weather.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ar.farmguard.core.presentation.getCurrentTime
import com.ar.farmguard.core.presentation.shared.components.ContentCard

@Composable
fun CelestialRiseAnimation(
    riseTime: String?,
    setTime: String?,
    isSun: Boolean,
    modifier: Modifier = Modifier
){

    if(riseTime != null && setTime != null){
        val currentTime = remember { getCurrentTime() }

        val animatable = remember { Animatable(initialValue = 0f) }

        LaunchedEffect(key1 = true) {
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = 0.8f,
                    stiffness = 1f
                )
            )
        }

        ContentCard(
            onClick = {},
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Spacer(
                    modifier =
                    Modifier
                        .padding(horizontal = 12.dp)
                        .aspectRatio(3 /2f)
                        .drawWithCache {
                            onDrawBehind {
                                drawSunPath(
                                    riseTime = riseTime,
                                    setTime = setTime,
                                    currentTime = currentTime,
                                    isSun = isSun,
                                    animateProgress = {
                                        animatable.value
                                    }
                                )
                            }
                        }
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if(isSun) "Sunrise" else "Moonrise",
                            modifier = Modifier.offset { IntOffset(0, 10) },
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp
                            ),
                        )
                        Text(
                            text = riseTime,
                            modifier = Modifier,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if(isSun) "Sunset" else "Moonset",
                            modifier = Modifier.offset { IntOffset(0, 10) },
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp
                            ),
                        )
                        Text(
                            text = setTime,
                            modifier = Modifier,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}