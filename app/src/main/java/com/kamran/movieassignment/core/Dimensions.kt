package com.kamran.movieassignment.core

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceSmallPlus: Dp = 12.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceMediumPlus: Dp = 12.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,

    val movieListItemImageHeight: Dp = 250.dp,
    val movieDetailImageHeight: Dp = 460.dp,
)

val LocalDimensions = compositionLocalOf { Dimensions() }