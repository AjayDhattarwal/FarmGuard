package com.ar.farmguard.ui.shared.components

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
    columns: Int = 3,
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
        val padding = contentPadding.calculateLeftPadding(LayoutDirection.Ltr)
        val itemWidth = (constraints.maxWidth - padding.roundToPx()) / columns
        val itemConstraints = constraints.copy(
            minWidth = itemWidth,
            maxWidth = itemWidth
        )

        val verticalSpacingPx = verticalArrangement.spacing.roundToPx()
        val horizontalSpacingPx = horizontalArrangement.spacing.roundToPx()

        val placeable = measures.map { it.measure(itemConstraints) }
        val rows = (placeable.size + columns - 1) / columns

        val gridHeight = rows * itemWidth + (rows - 1) * verticalSpacingPx


        layout(constraints.maxWidth,  gridHeight) {
            placeable.forEachIndexed { index, placeable ->
                val row = index / columns
                val column = index % columns

                val x = column * (itemWidth + horizontalSpacingPx)
                val y = row * (placeable.height + verticalSpacingPx)

                placeable.place(x, y)
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
