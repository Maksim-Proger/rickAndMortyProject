package com.example.project.presentation.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.project.presentation.components.CharacterItem
import com.example.project.presentation.components.CustomTopAppBar
import com.example.project.presentation.navigation.Route
import com.example.project.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenScaffold(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val searchText by viewModel.searchText.collectAsState()
    val characters = viewModel.characters.collectAsLazyPagingItems()
    val isRefreshing = characters.loadState.refresh is LoadState.Loading
    val refreshState = rememberPullToRefreshState()
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                scrollBehavior = scrollBehavior,
                title = "Главный",
                searchText = searchText,
                onSearchTextChange = viewModel::onSearchTextChange
            )
        },
//        floatingActionButton = {
//            FABFilter(
//                painterIcon = painterResource(R.drawable.icon_filter),
//                expanded = menuExpanded,
//                onExpandedChange = { menuExpanded = it },
//                onButtonClick = {
//                    coroutineScope.launch {
//                        listState.animateScrollToItem(0)
//                    }
//                    menuExpanded = !menuExpanded
//                }
//            )
//        },
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            PullToRefreshBox(
                state = refreshState,
                isRefreshing = isRefreshing,
                onRefresh = { characters.refresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyVerticalGrid(
                    state = listState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(characters.itemCount) { index ->
                        val el = characters[index]
                        el?.let {
                            CharacterItem(el) {
                                navController.navigate(
                                    Route.DetailCardScaffold.getItem(itemId = el.id)
                                )
                            }
                        }
                    }
                    when(val append = characters.loadState.append) {
                        is LoadState.Loading -> {
                            item(span = { GridItemSpan(2) }) {
                                Box(
                                    Modifier.fillMaxWidth().padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item(span = { GridItemSpan(2) }) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Ошибка загрузки: ${append.error.localizedMessage}",
                                        color = MaterialTheme.colorScheme.error,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Button(onClick = { characters.retry() }) {
                                        Text("Повторить")
                                    }
                                }
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}