package com.example.moodtracker

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoodTrackerScreenNavigationTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupMoodTrackerAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MoodTrackerApp(navController = navController)
        }
    }

    @Test
    fun moodTrackerNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(MoodTrackerScreen.Start.name)
    }

    @Test
    fun moodTrackerNavHost_verifyBackNavigationNotShownOnStartMoodTrackerScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun moodTrackerNavHost_clickStartMoodTracker_navigatesToChooseMoodScreen() {
        composeTestRule.onNodeWithStringId(R.string.start_mood_tracker).performClick()
        navController.assertCurrentRouteName(MoodTrackerScreen.ChooseMood.name)
    }

    @Test
    fun moodTrackerNavHost_clickSubmitOnChooseMoodScreen_navigatesToMoodCheckoutScreen() {
        navigateToChooseMoodScreen()
        composeTestRule.onNodeWithStringId(R.string.submit).performClick()
        navController.assertCurrentRouteName(MoodTrackerScreen.MoodCheckout.name)
    }

    @Test
    fun moodTrackerNavHost_clickCancelOnChooseMoodScreen_navigatesToStartMoodTrackerScreen() {
        navigateToChooseMoodScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(MoodTrackerScreen.Start.name)
    }

    @Test
    fun moodTrackerNavHost_clickBackOnChooseMoodScreen_navigatesToStartMoodTrackerScreen() {
        navigateToChooseMoodScreen()
        performNavigateBack()
        navController.assertCurrentRouteName(MoodTrackerScreen.Start.name)
    }

    @Test
    fun moodTrackerNavHost_clickBackOnMoodCheckoutScreen_navigatesToChooseMoodScreen() {
        navigateToMoodCheckoutScreen()
        performNavigateBack()
        navController.assertCurrentRouteName(MoodTrackerScreen.ChooseMood.name)
    }

    @Test
    fun moodTrackerNavHost_clickOkOnMoodCheckoutScreen_navigatesToStartMoodTrackerScreen() {
        navigateToMoodCheckoutScreen()
        composeTestRule.onNodeWithStringId(R.string.ok).performClick()
        navController.assertCurrentRouteName(MoodTrackerScreen.Start.name)
    }

    private fun navigateToChooseMoodScreen() {
        composeTestRule.onNodeWithStringId(R.string.start_mood_tracker).performClick()
        composeTestRule.onNodeWithStringId(R.string.happy).performClick()
    }

    private fun navigateToMoodCheckoutScreen() {
        navigateToChooseMoodScreen()
        composeTestRule.onNodeWithStringId(R.string.submit).performClick()
    }

    private fun performNavigateBack() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }
}