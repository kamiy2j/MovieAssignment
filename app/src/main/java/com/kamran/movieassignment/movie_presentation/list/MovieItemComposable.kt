package com.kamran.movieassignment.movie_presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.kamran.movieassignment.R
import com.kamran.movieassignment.core.LocalDimensions
import com.kamran.movieassignment.core.TestTags.MOVIE_LIST_ITEM
import com.kamran.movieassignment.core.utils.UiText
import com.kamran.movieassignment.movie_domain.model.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItemComposable(
    movie: MovieItem,
    onItemClick: () -> Unit
) {
    val typography = MaterialTheme.typography
    val dimensions = LocalDimensions.current
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .testTag(MOVIE_LIST_ITEM)
            .fillMaxWidth()
            .padding(dimensions.spaceSmall),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensions.spaceExtraSmall
        ),
        shape = RoundedCornerShape(dimensions.spaceSmall),
        onClick = { onItemClick() }
    ) {

        Box(modifier = Modifier.height(dimensions.movieListItemImageHeight)) {

            Image(
                painter = rememberAsyncImagePainter(movie.posterPath),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(dimensions.spaceSmall)),
                contentScale = ContentScale.Crop
            )

            if (movie.adult) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensions.spaceSmall),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = Color.Red,
                                shape = RoundedCornerShape(dimensions.spaceSmall)
                            )
                            .padding(dimensions.spaceExtraSmall),
                        text = UiText.StringResource(R.string.adult_movie_label).asString(context),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 400f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensions.spaceSmall),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = movie.title,
                    color = Color.White,
                    style = typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = dimensions.spaceSmall),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}