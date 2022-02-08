package com.example.instaflix.data

import androidx.annotation.DrawableRes

class Movie (
    val id: String,
    val title: String,
    @DrawableRes
    val poster: Int?,
    val description: String,
    val synopsis: String

        )