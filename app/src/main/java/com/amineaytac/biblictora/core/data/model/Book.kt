package com.amineaytac.biblictora.core.data.model

data class Book(
    val id: Int,
    val authors: String,
    val bookshelves: List<String>,
    val languages: String,
    val title: String,
    val formats: ReadFormats,
    val image: String
)