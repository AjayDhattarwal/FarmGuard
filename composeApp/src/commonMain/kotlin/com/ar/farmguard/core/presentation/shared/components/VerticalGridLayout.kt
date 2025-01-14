package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun VerticalGridLayout(
    columns: Int,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: GridScope.() -> Unit
) {
    val gridScope = GridScopeImpl().apply(content)
    val items = gridScope.gridItems

    Layout(
        content = {
            items.forEach { it() }
        },
        modifier = modifier.fillMaxWidth()
    ) { measures, constraints ->
        val leftPadding = contentPadding.calculateLeftPadding(LayoutDirection.Ltr).roundToPx()
        val rightPadding = contentPadding.calculateRightPadding(LayoutDirection.Ltr).roundToPx()
        val topPadding = contentPadding.calculateTopPadding().roundToPx()
        val bottomPadding = contentPadding.calculateBottomPadding().roundToPx()

        val horizontalSpacingPx = horizontalArrangement.spacing.roundToPx()
        val verticalSpacingPx = verticalArrangement.spacing.roundToPx()

        // Calculate available width for items
        val availableWidth = constraints.maxWidth - leftPadding - rightPadding
        val itemWidth = (availableWidth - (columns - 1) * horizontalSpacingPx) / columns
        val itemConstraints = constraints.copy(
            minWidth = itemWidth,
            maxWidth = itemWidth
        )

        val placeables = measures.map { it.measure(itemConstraints) }
        val rowHeights = IntArray((placeables.size + columns - 1) / columns) { 0 }

        placeables.forEachIndexed { index, placeable ->
            val row = index / columns
            rowHeights[row] = maxOf(rowHeights[row], placeable.height)
        }

        val gridHeight = topPadding + bottomPadding + rowHeights.sum() +
                (rowHeights.size - 1) * verticalSpacingPx

        layout(constraints.maxWidth, gridHeight) {
            var yOffset = topPadding
            placeables.forEachIndexed { index, placeable ->
                val row = index / columns
                val column = index % columns

                if (column == 0 && index != 0) {
                    yOffset += rowHeights[row - 1] + verticalSpacingPx
                }

                val x = leftPadding + column * (itemWidth + horizontalSpacingPx)
                placeable.place(x, yOffset)
            }
        }
    }
}

interface GridScope {
    fun item(content: @Composable () -> Unit)
    fun items(count: Int, itemContent: @Composable (index: Int) -> Unit)
    fun <T> items(items: List<T>, itemContent: @Composable (item: T) -> Unit)
}

class GridScopeImpl : GridScope {
    val gridItems = mutableListOf<@Composable () -> Unit>()

    override fun item(content: @Composable () -> Unit) {
        gridItems.add(content)
    }

    override fun items(count: Int, itemContent: @Composable (index: Int) -> Unit) {
        repeat(count) { index ->
            gridItems.add { itemContent(index) }
        }
    }

    override fun <T> items(items: List<T>, itemContent: @Composable (item: T) -> Unit) {
        items.forEach { item ->
            gridItems.add { itemContent(item) }
        }
    }
}
