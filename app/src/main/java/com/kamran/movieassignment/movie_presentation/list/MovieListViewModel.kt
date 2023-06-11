package com.kamran.movieassignment.movie_presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kamran.movieassignment.R
import com.kamran.movieassignment.core.utils.UiEvent
import com.kamran.movieassignment.core.utils.UiText
import com.kamran.movieassignment.movie_domain.use_cases.MovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    movieUseCases: MovieUseCases
) : ViewModel() {

    var state by mutableStateOf(MovieListState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val movieItems = movieUseCases
        .fetchMovies()
        .cachedIn(viewModelScope)


    fun onRefreshError() {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.ShowSnackbar(
                    UiText.StringResource(R.string.snackbar_error_message)
                )
            )
        }
    }

    fun onLoadMoreError() {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.ShowToast(
                    UiText.StringResource(R.string.load_more_error_message)
                )
            )
        }
    }

}