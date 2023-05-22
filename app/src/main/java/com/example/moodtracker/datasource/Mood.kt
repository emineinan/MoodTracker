package com.example.moodtracker.datasource

import androidx.annotation.StringRes

data class Mood(
    @StringRes val name: Int,
    @StringRes val description: Int
)