package com.ar.farmguard.home.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.ar.farmguard.core.presentation.shared.components.IconThemeButton
import com.ar.farmguard.app.utils.greetingMessage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    userName: String,
    profileImageUrl: String,
    onSearchClicked: () -> Unit,
    onNotificationClicked: () -> Unit
) {
    val greeting by remember {
        derivedStateOf { greetingMessage() }
    }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(profileImageUrl),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary.copy(0.7f)
                )
                Text(
                    text = userName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Row {
            IconThemeButton(
                icon = Icons.Default.Search,
                contentDescription = "Search",
                onClick = onSearchClicked
            )
            IconThemeButton(
                icon = Icons.Default.Notifications,
                contentDescription = "Notifications",
                onClick = onNotificationClicked
            )
        }
    }

}


@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar(
        userName = "John Doe",
        profileImageUrl = "https://example.com/profile.jpg",
        onSearchClicked = {},
        onNotificationClicked = {}
    )
}
