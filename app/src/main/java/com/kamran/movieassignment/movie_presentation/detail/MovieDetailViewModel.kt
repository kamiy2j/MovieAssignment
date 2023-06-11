package com.kamran.movieassignment.movie_presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamran.movieassignment.R
import com.kamran.movieassignment.core.utils.UiEvent
import com.kamran.movieassignment.core.utils.UiText
import com.kamran.movieassignment.movie_domain.model.MovieDetail
import com.kamran.movieassignment.movie_domain.use_cases.MovieUseCases

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val MovieUseCases: MovieUseCases
) : ViewModel() {

    var state by mutableStateOf(MovieDetailState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun refreshMovieDetail(movieId: Int) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isLoadingFailed = false,
                movieDetail = MovieDetail()
            )
            MovieUseCases.getMovieDetail(movieId).onSuccess {
                state = state.copy(
                    isLoading = false,
                    movieDetail = it
                )
            }.onFailure {
                state = state.copy(
                    isLoading = false,
                    isLoadingFailed = true
                )
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.snackbar_error_message)
                    )
                )
            }
        }
    }
}