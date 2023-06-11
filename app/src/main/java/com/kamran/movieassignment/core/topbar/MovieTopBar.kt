package com.kamran.movieassignment.core.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.kamran.movieassignment.R
import com.kamran.movieassignment.core.Mirage
import com.kamran.movieassignment.core.utils.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopBar(
    navController: NavController,
) {
    val context = LocalContext.current
    var canPop by remember {
        mutableStateOf(false)
    }
    var destination by remember {
        mutableStateOf("")
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
            canPop = controller.previousBackStackEntry != null
            destination = controller.currentBackStackEntry?.destination?.route ?: ""
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    TopAppBar(
        title = {
            Text(
                text = when (destination) {
                    "detail/{movieId}" -> {
                        UiText.StringResource(R.string.movie_detail_title)
                            .asString(context)
                    }

                    else -> {
                        UiText.StringResource(R.string.top_bar_title)
                            .asString(context)
                    }
                }
            )
        },
        navigationIcon = {
            if (canPop) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            } else {
                null
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Mirage,
            titleContentColor = Color.LightGray,
            navigationIconContentColor = Color.White
        )
    )
}