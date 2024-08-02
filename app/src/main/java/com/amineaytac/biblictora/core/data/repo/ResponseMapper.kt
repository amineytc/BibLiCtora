package com.amineaytac.biblictora.core.data.repo

import com.amineaytac.biblictora.core.data.model.Book
import com.amineaytac.biblictora.core.data.model.ReadFormats
import com.amineaytac.biblictora.core.network.dto.Author
import com.amineaytac.biblictora.core.network.dto.BookResponse
import com.amineaytac.biblictora.core.network.dto.Formats
import retrofit2.Response

typealias RestBooksResponse = Response<BookResponse>
typealias AuthorsResponse = List<Author?>?
typealias LanguageResponse = List<String?>?

fun RestBooksResponse.toBookList(): List<Book> {
    return body()!!.results!!.map { book ->
        val bookshelves = book?.bookshelves?.map {
            if (it.isNullOrEmpty()) {
                ""
            } else {
                it
            }
        } ?: emptyList()

        Book(
            book?.id ?: -1,
            book?.authors.toAuthorString(),
            bookshelves,
            book?.languages.toLanguageString(),
            book?.title.orEmpty(),
            book?.formats?.toReadFormats() ?: ReadFormats("", ""),
            book?.formats?.imagejpeg.toString()
        )
    }
}

fun AuthorsResponse.toAuthorString(): String {
    var authors = ""

    this?.forEachIndexed { index, author ->
        val name = author?.name?.split(", ")
        if(name?.size == 1){
            authors += name[0]
        }else {
            authors += name?.get(1) ?: ""
            authors += " "
            authors += name?.get(0) ?: ""
            if (this.size != index + 1) {
                authors += ", "
            }
        }
    }
    return authors
}

fun LanguageResponse.toLanguageString(): String {
    var language = ""
    this?.forEachIndexed { index, s ->
        language += s?.uppercase()
        if (this.size != index + 1) {
            language += ", "
        }
    }
    return language
}

fun Formats.toReadFormats(): ReadFormats {
    return ReadFormats(
        this.texthtml.toString(),
        this.texthtmlCharsetutf8.toString()
    )
}