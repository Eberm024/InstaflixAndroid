package com.example.instaflix.data

import com.parse.ParseUser

data class Comment (
    val objectId: String,
    val movieId: Int,
    val text: String,
    val author: ParseUser
        )