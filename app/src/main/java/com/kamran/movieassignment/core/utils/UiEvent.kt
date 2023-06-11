package com.kamran.movieassignment.core.utils

sealed class UiEvent {
    data class ShowSnackbar(val message: UiText): UiEvent()
    data class ShowToast(val message: UiText): UiEvent()
}