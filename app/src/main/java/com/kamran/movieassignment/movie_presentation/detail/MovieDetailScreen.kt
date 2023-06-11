package com.kamran.movieassignment.movie_presentation.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.kamran.movieassignment.R
import com.kamran.movieassignment.core.LocalDimensions
import com.kamran.movieassignment.core.TestTags.MOVIE_DETAIL
import com.kamran.movieassignment.core.Tuna
import com.kamran.movieassignment.core.utils.UiEvent
import com.kamran.movieassignment.core.utils.UiText
import com.kamran.movieassignment.movie_presentation.components.EmptyScreenMessage
import com.kamran.movieassignment.movie_presentation.components.LoadingIndicator

@Composable
fun MovieDetailScreen(
    snackbarHostState: SnackbarHostState,
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val typography = MaterialTheme.typography
    val dimensions = LocalDimensions.current
    val context = LocalContext.current

    val movieDetail = viewModel.state.movieDetail

    LaunchedEffect(true) {
        viewModel.refreshMovieDetail(movieId)

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
                            viewModel.refreshMovieDetail(movieId)
                        }

                        SnackbarResult.Dismissed -> {
                            Log.d("Snackbar", "Dismissed")
                        }
                    }
                }

                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Tuna),
        contentAlignment = Alignment.Center
    ) {

        when {
            viewModel.state.isLoading -> {
                LoadingIndicator()
            }

            viewModel.state.isLoadingFailed -> {
                EmptyScreenMessage(
                    UiText.StringResource(R.string.movie_detail_empty_screen_message)
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensions.spaceMedium)
                        .testTag(MOVIE_DETAIL)
                ) {

                    Box(modifier = Modifier.height(dimensions.movieDetailImageHeight)) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = movieDetail.posterPath,
                                error = painterResource(R.drawable.movieplaceholder),
                            ),
                            contentDescription = movieDetail.title,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(dimensions.spaceMediumPlus))
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensions.spaceSmall))

                    Text(
                        text = movieDetail.title,
                        style = typography.headlineMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )


                    Spacer(modifier = Modifier.height(dimensions.spaceSmall))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = movieDetail.genres,
                            style = typography.bodyMedium,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold
                        )

                        if (movieDetail.adult) {
                            Text(
                                text =
                                UiText.StringResource(
                                    R.string.adult_movie_label
                                ).asString(context),
                                color = Color.Red,
                                style = typography.bodyMedium,
                                modifier = Modifier.padding(top = dimensions.spaceExtraSmall)
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(dimensions.spaceExtraSmall))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text =
                            UiText.StringResource(R.string.released_label).asString(context) +
                                    " ${movieDetail.releaseDate}",
                            style = typography.bodySmall,
                            color = Color.LightGray
                        )


                        Text(
                            text = movieDetail.runtime,
                            style = typography.bodySmall,
                            color = Color.LightGray
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensions.spaceSmallPlus))

                    Text(
                        text = movieDetail.overview,
                        style = typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }

    }

}
