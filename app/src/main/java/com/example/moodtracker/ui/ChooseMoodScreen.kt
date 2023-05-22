package com.example.moodtracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.moodtracker.datasource.Mood
import com.example.moodtracker.R

@Composable
fun ChooseMoodScreen(
    options: List<Mood>,
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onSubmitButtonClicked: () -> Unit = {}
) {

    var selectedItemName by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = modifier) {
        options.forEach { item ->
            val onClick = {
                selectedItemName = context.resources.getString(item.name)
            }
            ChooseMoodItemRow(
                item = item,
                selectedItemName = selectedItemName,
                onClick = onClick,
                modifier = Modifier.selectable(
                    selected = selectedItemName == context.resources.getString(item.name),
                    onClick = onClick
                )
            )
        }

        ChooseMoodScreenButtonGroup(
            selectedItemName = selectedItemName,
            onCancelButtonClicked = onCancelButtonClicked,
            onSubmitButtonClicked = {
                onSubmitButtonClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
fun ChooseMoodItemRow(
    item: Mood,
    selectedItemName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        RadioButton(
            selected = selectedItemName == context.resources.getString(item.name),
            onClick = onClick
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = context.resources.getString(item.name),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = context.resources.getString(item.description),
                style = MaterialTheme.typography.body1
            )
            Divider(
                thickness = dimensionResource(R.dimen.thickness_divider),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun ChooseMoodScreenButtonGroup(
    selectedItemName: String,
    onCancelButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        OutlinedButton(modifier = Modifier.weight(1f), onClick = onCancelButtonClicked) {
            Text(stringResource(R.string.cancel))
        }
        Button(
            modifier = Modifier.weight(1f),
            // the button is enabled when the user makes a selection
            enabled = selectedItemName.isNotEmpty(),
            onClick = onSubmitButtonClicked
        ) {
            Text(stringResource(R.string.submit))
        }
    }
}
