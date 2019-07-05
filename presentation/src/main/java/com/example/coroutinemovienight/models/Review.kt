package com.example.coroutinemovienight.models

data class Review(
        var id: String,
        var author: String,
        var content: String? = null
)