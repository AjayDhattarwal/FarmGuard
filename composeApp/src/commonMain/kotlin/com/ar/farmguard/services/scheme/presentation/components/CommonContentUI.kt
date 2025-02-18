package com.ar.farmguard.services.scheme.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ar.farmguard.services.scheme.domain.model.ApplicationProcess
import com.ar.farmguard.services.scheme.domain.model.ListCommon




@Composable
fun CommonApplicationUI(applications: List<ApplicationProcess>) {
    var selectedIndex by remember { mutableStateOf(0) }
    Column{
        Row {
            applications.forEachIndexed { index, item ->
                FilterChip(
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index },
                    label = { Text(text = item.mode) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        AnimatedContent(
            targetState = selectedIndex
        ) {
            CommonContentUI(applications[selectedIndex].process)
        }
    }

}

@Composable
fun CommonContentUI(content: List<ListCommon>) {
    Column(modifier = Modifier.padding(4.dp)) {
        content.forEach { item ->
            RenderCommonItem(item)
        }
    }
}

@Composable
fun RenderCommonItem(item: ListCommon, type: String = "", index: Int = 0) {
    when (item.type) {
        "paragraph" -> {
            if (item.text != null) {
                Text(
                    text = item.text,
                    fontWeight = if (item.bold) FontWeight.Bold else FontWeight.Normal,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                item.children?.forEach { listItem ->
                    listItem.text?.let {
                        Text(
                            text = it,
                            fontWeight = if (item.bold) FontWeight.Bold else FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

        }

        "ol_list" -> {
            Column(modifier = Modifier.padding(start = 8.dp)) {
                item.children?.forEachIndexed { index, listItem ->
                    RenderCommonItem(listItem, "ol_list", index = index)
                }
            }
        }


        "list_item" -> {

            val point = when (type) {
                "list_item" -> "- "
                "ol_list" -> "${index + 1}. "
                "ul_list" -> "• "
                else -> null
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                point?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column {
                    item.children?.forEachIndexed { idx, listItem ->
                        if (listItem.text != null) {
                            Text(
                                modifier = Modifier,
                                text = listItem.text,
                                fontWeight = if (listItem.bold) FontWeight.Bold else FontWeight.Normal,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }

            }

        }

        "ul_list" -> {
            Column(modifier = Modifier.padding(start = 8.dp)) {
                item.children?.forEachIndexed { idx, value ->
                    RenderCommonItem(value, "ul_list", idx)
                }

            }
        }
        else -> {
            item.children?.forEach {
                RenderCommonItem(it)
            }
        }
    }
}