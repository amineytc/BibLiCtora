package com.amineaytac.biblictora.core.network.source.rest

import com.amineaytac.biblictora.core.network.dto.BookResponse
import retrofit2.Response
import retrofit2.http.GET

interface BookRestApi {
    @GET(".")
    suspend fun getAllBooks(): Response<BookResponse>
}