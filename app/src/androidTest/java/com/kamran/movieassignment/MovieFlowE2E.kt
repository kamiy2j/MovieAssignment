package com.kamran.movieassignment

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kamran.movieassignment.core.AppConstants.MOVIE_LIST_PAGE_SIZE
import com.kamran.movieassignment.core.TestTags.MOVIE_DETAIL
import com.kamran.movieassignment.core.TestTags.MOVIE_LIST
import com.kamran.movieassignment.core.TestTags.MOVIE_LIST_ITEM
import com.kamran.movieassignment.utils.turnWifiOff
import com.kamran.movieassignment.utils.turnWifiOn
import com.kamran.movieassignment.utils.waitUntilTimeout
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieFlowE2E {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val apiTimeOutPeriod: Long = 5000


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testMovieListToDetailNavigation() {

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(MOVIE_LIST_ITEM), apiTimeOutPeriod)

        composeTestRule.onAllNodesWithTag(MOVIE_LIST_ITEM).onFirst().assertIsDisplayed()

        composeTestRule.onAllNodesWithTag(MOVIE_LIST_ITEM).onFirst().performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(MOVIE_DETAIL), apiTimeOutPeriod)

        composeTestRule.onNodeWithTag(MOVIE_DETAIL).assertIsDisplayed()

        Espresso.pressBack()

        composeTestRule.onAllNodesWithTag(MOVIE_LIST_ITEM).onFirst().assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testMovieListToDetailContent() {

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(MOVIE_LIST_ITEM), apiTimeOutPeriod)

        composeTestRule.onAllNodesWithText("John Wick: Chapter 4").onFirst().assertIsDisplayed()

        composeTestRule.onAllNodesWithText("John Wick: Chapter 4").onFirst().performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(MOVIE_DETAIL), apiTimeOutPeriod)

        composeTestRule.onNodeWithText("John Wick: Chapter 4").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes."
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText("Action | Thriller | Crime").assertIsDisplayed()
        composeTestRule.onNodeWithText("2h 50min").assertIsDisplayed()

        Espresso.pressBack()

        composeTestRule.onAllNodesWithTag(MOVIE_LIST_ITEM).onFirst().assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testPageLoadFailedMovieList() {

        composeTestRule.turnWifiOff()

        composeTestRule.waitUntilExactlyOneExists(
            hasText("Oops, something went wrong!"),
            apiTimeOutPeriod
        )

        composeTestRule.onNodeWithText("Oops, something went wrong!").assertIsDisplayed()

        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()

        composeTestRule.onNodeWithText("Failed to load the movie list ☹").assertIsDisplayed()

        composeTestRule.turnWifiOn()

        composeTestRule.onNodeWithText("Retry").performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(MOVIE_LIST_ITEM), apiTimeOutPeriod)

        composeTestRule.onAllNodesWithTag(MOVIE_LIST_ITEM).onFirst().assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testPageLoadFailedMovieDetail() {

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(MOVIE_LIST_ITEM), apiTimeOutPeriod)

        composeTestRule.turnWifiOff()

        composeTestRule.onAllNodesWithTag(MOVIE_LIST_ITEM).onFirst().assertIsDisplayed()

        composeTestRule.onAllNodesWithTag(MOVIE_LIST_ITEM).onFirst().performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasText("Oops, something went wrong!"),
            apiTimeOutPeriod
        )

        composeTestRule.onNodeWithText("Oops, something went wrong!").assertIsDisplayed()

        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()

        composeTestRule.onNodeWithText("Failed to load the movie details ☹").assertIsDisplayed()

        composeTestRule.turnWifiOn()

        composeTestRule.onNodeWithText("Retry").performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(MOVIE_DETAIL), apiTimeOutPeriod)

        composeTestRule.onNodeWithTag(MOVIE_DETAIL).assertIsDisplayed()

        Espresso.pressBack()

        composeTestRule.onAllNodesWithTag(MOVIE_LIST_ITEM).onFirst().assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testMovieListPaginationScrolling() {

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(MOVIE_LIST_ITEM), apiTimeOutPeriod)

        composeTestRule.onNodeWithTag(MOVIE_LIST).performScrollToIndex(MOVIE_LIST_PAGE_SIZE)

        composeTestRule.waitUntilTimeout(apiTimeOutPeriod)

        composeTestRule.onNodeWithTag(MOVIE_LIST).performScrollToIndex(MOVIE_LIST_PAGE_SIZE * 2)

        composeTestRule.waitUntilTimeout(apiTimeOutPeriod)

        composeTestRule.onNodeWithTag(MOVIE_LIST).performScrollToIndex(MOVIE_LIST_PAGE_SIZE * 3)

        composeTestRule.waitUntilTimeout(apiTimeOutPeriod)

        composeTestRule.onNodeWithTag(MOVIE_LIST).performScrollToIndex(0)

    }

    @After
    fun tearDown() {
        composeTestRule.turnWifiOn()
    }

}