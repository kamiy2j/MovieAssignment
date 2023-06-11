package com.kamran.movieassignment.movie_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import com.kamran.movieassignment.core.LocalDimensions
import com.kamran.movieassignment.core.TestTags.EMPTY_SCREEN_MESSAGE
import com.kamran.movieassignment.core.utils.UiText

@Composable
fun EmptyScreenMessage(
    messageResourceId: UiText
) {

    val typography = MaterialTheme.typography
    val context = LocalContext.current
    val dimensions = LocalDimensions.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(EMPTY_SCREEN_MESSAGE),
        contentAlignment = Alignment.Center

    ) {
        Text(
            modifier = Modifier
                .padding(dimensions.spaceLarge),
            text = messageResourceId.asString(context),
            color = Color.White,
            style = typography.headlineLarge,
            textAlign = TextAlign.Center,
        )
    }
}