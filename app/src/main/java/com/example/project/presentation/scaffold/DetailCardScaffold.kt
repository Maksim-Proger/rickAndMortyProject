package com.example.project.presentation.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.project.presentation.components.CustomTopAppBar
import com.example.project.presentation.viewmodel.MainViewModel
import com.example.project.utils.WorkerWithImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCardScaffold(
    navController: NavHostController,
    itemId: Int,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val character by viewModel.character.collectAsState()

    LaunchedEffect(itemId) {
        viewModel.getCharacterById(itemId)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                scrollBehavior = scrollBehavior,
                title = "Информация о персонаже",
                isActiveButtonBack = true,
                buttonBack = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Spacer(Modifier.padding(innerPadding))

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            character?.let {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box {
                        WorkerWithImage(
                            it,
                            300.dp
                        )
                        Text(
                            text = it.status,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 7.dp)
                ) {
                    Spacer(Modifier.padding(vertical = 7.dp))
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.padding(vertical = 7.dp))
                    Text(
                        text = it.gender,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.padding(vertical = 7.dp))
                    Text(
                        text = it.status,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.padding(vertical = 7.dp))
                    Text(
                        text = it.species,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}