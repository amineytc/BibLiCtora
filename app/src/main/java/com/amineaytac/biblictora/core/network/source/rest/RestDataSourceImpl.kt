package com.amineaytac.biblictora.core.network.source.rest

import com.amineaytac.biblictora.core.network.dto.BookResponse
import retrofit2.Response
import javax.inject.Inject

class RestDataSourceImpl @Inject constructor(private val bookRestApi: BookRestApi) :
    RestDataSource {

    override suspend fun getAllBooks(): Response<BookResponse> {
        return bookRestApi.getAllBooks()
    }

    override suspend fun getBooksWithSearch(search: String): Response<BookResponse> {
        return bookRestApi.getBooksWithSearch(search)
    }

    override suspend fun getBooksWithLanguages(languages: List<String>): Response<BookResponse> {
        return bookRestApi.getBooksWithLanguages(languages)
    }
}