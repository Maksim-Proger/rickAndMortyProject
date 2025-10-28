package com.example.project.presentation.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.presentation.components.CustomTopAppBar
import com.example.project.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedSearchScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    var searchWordResult by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Поиск по фильтрам",
                isActiveButtonBack = true,
                buttonBack = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.applyFilter(
                        name = searchWordResult.ifBlank { null },
                        status = selectedStatus.ifBlank { null },
                        gender = selectedGender.ifBlank { null }
                    )
                    navController.popBackStack()
                }
            ) {
                Text("OK")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilterByName { newSearchWord ->
                searchWordResult = newSearchWord
            }

            FilterByStatus { status ->
                selectedStatus = status
            }

            FilterByGenre { gender ->
                selectedGender = gender
            }
        }
    }
}


@Composable
fun FilterByName(
    searchWordResult: (String) -> Unit,
) {
    val (searchWord, setSearchWord) = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        shape = RoundedCornerShape(50),
        value = searchWord,
        onValueChange = setSearchWord,
        textStyle = MaterialTheme.typography.bodyMedium,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
//                tint = MaterialTheme.colorScheme.onSecondary
            )
        },
        trailingIcon = if (searchWord.isNotEmpty()) {
            {
                IconButton(
                    onClick = {
                        setSearchWord("")
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        } else null,
        label = {
            Text(
                text = "Поиск ...",
                style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSecondary
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                searchWordResult(searchWord)
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        singleLine = true
    )
}

@Composable
fun FilterByStatus(
    onSelectedStatus: (String) -> Unit
) {
    var selected1 by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(false) }
    var selected3 by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        FilterChip(
            label = {
                Text(
                    text = "Alive",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            selected = selected1,
            onClick = {
                selected1 = !selected1
                onSelectedStatus("Alive")
            },
        )
        FilterChip(
            label = {
                Text(
                    text = "Dead",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            selected = selected2,
            onClick = {
                selected2 = !selected2
                onSelectedStatus("Dead")
            },
        )
        FilterChip(
            label = {
                Text(
                    text = "unknown",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            selected = selected3,
            onClick = {
                selected3 = !selected3
                onSelectedStatus("unknown")
            },
        )
    }
}

@Composable
fun FilterByGenre(
    onSelectedGenre: (String) -> Unit
) {
    var selected1 by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(false) }
    var selected3 by remember { mutableStateOf(false) }
    var selected4 by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        FilterChip(
            label = {
                Text(
                    text = "Female",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            selected = selected1,
            onClick = {
                selected1 = !selected1
                onSelectedGenre("Female")
            },
        )
        FilterChip(
            label = {
                Text(
                    text = "Male",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            selected = selected2,
            onClick = {
                selected2 = !selected2
                onSelectedGenre("Male")
            },
        )
        FilterChip(
            label = {
                Text(
                    text = "genderless",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            selected = selected3,
            onClick = {
                selected3 = !selected3
                onSelectedGenre("genderless")
            },
        )
        FilterChip(
            label = {
                Text(
                    text = "unknown",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            selected = selected4,
            onClick = {
                selected4 = !selected4
                onSelectedGenre("unknown")
            },
        )
    }
}