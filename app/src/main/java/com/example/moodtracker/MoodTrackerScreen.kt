package com.example.moodtracker

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moodtracker.datasource.MoodDataSource
import com.example.moodtracker.ui.ChooseMoodScreen
import com.example.moodtracker.ui.MoodCheckoutScreen
import com.example.moodtracker.ui.StartMoodTrackerScreen

enum class MoodTrackerScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    ChooseMood(title = R.string.choose_mood),
    MoodCheckout(title = R.string.mood_checkout)
}

@Composable
fun MoodTrackerAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun MoodTrackerApp(navController: NavHostController = rememberNavController()) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MoodTrackerScreen.valueOf(
        backStackEntry?.destination?.route ?: MoodTrackerScreen.Start.name
    )

    Scaffold(
        topBar = {
            MoodTrackerAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MoodTrackerScreen.Start.name,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding),
        ) {
            composable(route = MoodTrackerScreen.Start.name) {
                StartMoodTrackerScreen(
                    onStartMoodTrackerButtonClicked = {
                        navController.navigate(MoodTrackerScreen.ChooseMood.name)
                    },
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxSize()
                )
            }

            composable(route = MoodTrackerScreen.ChooseMood.name) {
                ChooseMoodScreen(
                    options = MoodDataSource.moodItems,
                    onCancelButtonClicked = {
                        navController.popBackStack(MoodTrackerScreen.Start.name, inclusive = false)
                    },
                    onSubmitButtonClicked = {
                        navController.navigate(MoodTrackerScreen.MoodCheckout.name)
                    },
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .verticalScroll(rememberScrollState())
                )
            }

            composable(route = MoodTrackerScreen.MoodCheckout.name) {
                MoodCheckoutScreen(
                    onOkButtonClicked = {
                        navController.popBackStack(MoodTrackerScreen.Start.name, inclusive = false)
                    },
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}
