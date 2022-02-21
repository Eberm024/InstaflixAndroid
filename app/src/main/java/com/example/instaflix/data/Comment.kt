package com.example.instaflix.data

import com.parse.ParseUser

data class Comment (
    val movieId: Int,
    val text: String,
    val author: ParseUser
        )