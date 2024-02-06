package com.amineaytac.biblictora.core.data.model

data class Book(
    val authors: String,
    val bookshelves: List<String>,
    val id: Int,
    val languages: String,
    val title: String,
    val formats: ReadFormats,
    val image: String
)