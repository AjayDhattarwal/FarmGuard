package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ar.farmguard.app.utils.loadingEffect
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NoteText(
    modifier: Modifier = Modifier,
    text: String?,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    color:  Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(0.7f)
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        AnimatedContent(
            targetState = text == null,
            modifier = Modifier.fillMaxWidth()
        ){ loading ->
            if(loading){
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){

                    Icon(
                        imageVector =  Icons.Default.Star,
                        contentDescription = "gemini",
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {

                        Spacer(
                            Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(30))
                                .background(color.copy(0.2f)).height(16.dp)
                                .loadingEffect()
                                .padding(4.dp)
                        )
                        Spacer(Modifier.height(8.dp))

                        Spacer(
                            Modifier.fillMaxWidth(0.4f)
                                .clip(RoundedCornerShape(30))
                                .background(color.copy(0.2f)).height(16.dp)
                                .loadingEffect(
                                    targetValue = 400f
                                )
                                .padding(4.dp)
                        )
                    }
                }
            }else{
                Text(
                    text = text.toString(),
                    style = style,
                    color = color,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
