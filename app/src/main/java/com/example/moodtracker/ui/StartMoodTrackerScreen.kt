package com.example.moodtracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StartMoodTrackerScreen(
    onStartMoodTrackerButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onStartMoodTrackerButtonClicked,
            Modifier.widthIn(min = 250.dp)
        ) {
            Text(stringResource(com.example.moodtracker.R.string.start_mood_tracker))
        }
    }
}

@Preview
@Composable
fun StartMoodTrackerPreview() {
    StartMoodTrackerScreen(
        onStartMoodTrackerButtonClicked = {},
        modifier = Modifier
            .padding(dimensionResource(com.example.moodtracker.R.dimen.padding_medium))
            .fillMaxSize()
    )
}
