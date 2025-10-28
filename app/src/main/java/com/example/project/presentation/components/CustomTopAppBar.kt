package com.example.project.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    title: String,
    isActiveButtonBack: Boolean = false,
    searchText: String = "",
    onSearchTextChange: (String) -> Unit = {},
    buttonBack: () -> Unit = {}
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        actions = {
            if (!isActiveButtonBack) {
                Box(modifier = Modifier.widthIn(max = 220.dp)) {
                    SearchTextField(searchText, onSearchTextChange)
                }
            }
        },
        navigationIcon = {
            if (isActiveButtonBack) {
                IconButton(onClick = buttonBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            } else null
        }
    )
}

@Composable
private fun SearchTextField(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    val focusState = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .padding(end = 7.dp)
            .heightIn(min = 56.dp, max = 56.dp)
            .onFocusChanged { focusState.value = it.isFocused },
        shape = RoundedCornerShape(50),
        value = searchText,
        onValueChange = onSearchTextChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = if (searchText.isNotEmpty()) {
            {
                IconButton(
                    onClick = {
                        onSearchTextChange("")
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            }
        } else null,
        label = if (!focusState.value) {
            {
                Text(
                    text = "Поиск ...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true
    )
}