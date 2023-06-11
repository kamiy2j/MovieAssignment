package com.kamran.movieassignment.movie_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.kamran.movieassignment.core.CadmiumRed
import com.kamran.movieassignment.core.LocalDimensions
import com.kamran.movieassignment.core.TestTags.LOADER

@Composable
fun LoadingIndicator() {
    val dimensions = LocalDimensions.current
    Box(
        modifier = Modifier
            .testTag(LOADER)
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        CircularProgressIndicator(
            color = CadmiumRed,
            strokeWidth = dimensions.spaceExtraSmall
        )
    }
}