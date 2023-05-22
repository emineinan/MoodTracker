package com.example.moodtracker.datasource

import com.example.moodtracker.R

object MoodDataSource {
    val moodItems = listOf(
        Mood(
            name = R.string.happy,
            description = R.string.happy_des
        ),
        Mood(
            name = R.string.joyful,
            description = R.string.joyful_des
        ),
        Mood(
            name = R.string.confused,
            description = R.string.confused_des
        ),
        Mood(
            name = R.string.sad,
            description = R.string.sad_des
        )
    )
}