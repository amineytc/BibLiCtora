package com.amineaytac.biblictora.core.network.source.rest

import com.amineaytac.biblictora.core.network.dto.BookResponse
import retrofit2.Response

interface RestDataSource {

    suspend fun getAllBooks(): Response<BookResponse>
}