package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.ic_mic
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    placeholder: String = "Search or type crop",
    onSearch: (String) -> Unit,
) {

    var query by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Card (
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(30)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            leadingIcon()

            Box(
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                contentAlignment = Alignment.CenterStart
            ){
                BasicTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        onSearch(query)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    maxLines = 1,
                    enabled = true,
                    readOnly = false,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch(query)
                            focusManager.clearFocus()
                        },
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface)
                )


                if (query.isEmpty() ) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        maxLines = 1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }


            if (query.isNotEmpty() && isFocused) {
                IconButton(
                    onClick = {
                        query = ""
                    },
                    modifier = Modifier.size(20.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = Color.Gray
                    )
                }
            }else{
                trailingIcon()
            }
        }
    }
}


@Composable
fun AnimatedSearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    lazyState: LazyListState
){

    SearchField(
        onSearch = onSearch,
        placeholder = "Search or type crop",
        trailingIcon = {
            IconButton(
                onClick = {}
            ){
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_mic),
                    contentDescription = "mic"
                )
            }
        }
    )
}


@Preview
@Composable
fun SearchFieldPreview(){
    SearchField(
        onSearch = {},
        placeholder = "Search or type crop",
        trailingIcon = {
            IconButton(
                onClick = {}
            ){
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_mic),
                    contentDescription = "mic"
                )
            }
        }
    )
}