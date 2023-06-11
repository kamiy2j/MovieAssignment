package com.kamran.movieassignment.movie_presentation.list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kamran.movieassignment.R
import com.kamran.movieassignment.core.LocalDimensions
import com.kamran.movieassignment.core.Mirage
import com.kamran.movieassignment.core.TestTags.MOVIE_LIST
import com.kamran.movieassignment.core.Tuna
import com.kamran.movieassignment.core.utils.UiEvent
import com.kamran.movieassignment.core.utils.UiText
import com.kamran.movieassignment.movie_presentation.components.EmptyScreenMessage
import com.kamran.movieassignment.movie_presentation.components.LoadingIndicator

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieListScreen(
    snackbarHostState: SnackbarHostState,
    onItemClick: (movieId: Int) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val dimensions = LocalDimensions.current
    val context = LocalContext.current
    val movieItems = viewModel.movieItems.collectAsLazyPagingItems()
    val pullRefreshState = rememberPullRefreshState(
        viewModel.state.isRefreshing,
        {
            movieItems.refresh()
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = event.message
                            .asString(context),
                        actionLabel = UiText.StringResource(R.string.snackbar_action_message_retry)
                            .asString(context)
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            movieItems.refresh()
                        }

                        SnackbarResult.Dismissed -> {
                            Log.d("Snackbar", "Dismissed")
                        }
                    }
                }

                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Tuna, Mirage),
                    center = Offset(0f, 0f),
                    radius = Float.POSITIVE_INFINITY
                )
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(dimensions.default),
            modifier = Modifier.testTag(MOVIE_LIST)
        ) {
            items(movieItems.itemCount) { index ->
                movieItems[index]?.let { item ->
                    MovieItemComposable(
                        item,
                        onItemClick = { onItemClick(item.id) }
                    )
                }
            }

            when (movieItems.loadState.append) {
                is LoadState.Error -> {
                    viewModel.onLoadMoreError()
                }

                is LoadState.Loading -> {
                    item(span = {
                        GridItemSpan(maxLineSpan)
                    }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                }

                else -> Unit
            }
        }

        when (movieItems.loadState.refresh) {
            is LoadState.Error -> {
                viewModel.onRefreshError()
                EmptyScreenMessage(
                    UiText.StringResource(R.string.movie_list_empty_screen_message)
                )
            }

            is LoadState.Loading -> {
                LoadingIndicator()
            }

            else -> Unit
        }

        PullRefreshIndicator(
            viewModel.state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}