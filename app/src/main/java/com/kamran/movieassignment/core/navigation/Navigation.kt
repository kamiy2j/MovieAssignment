package com.kamran.movieassignment.core.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamran.movieassignment.movie_presentation.detail.MovieDetailScreen
import com.kamran.movieassignment.movie_presentation.list.MovieListScreen

@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = Route.HOME
    ) {
        composable(Route.HOME) {
            MovieListScreen(snackbarHostState, onItemClick = {
                navController.navigate(Route.DETAIL + "/${it}")
            })
        }
        composable(Route.DETAIL + "/{movieId}") {
            val movieId = it.arguments?.getString("movieId")
                ?.let { it1 -> Integer.parseInt(it1) }
                ?: 0

            MovieDetailScreen(snackbarHostState, movieId)
        }
    }
}