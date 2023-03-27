package org.tech.town.bookreview.data.api

import org.tech.town.bookreview.data.model.SearchBookDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookService {
    @GET("/v1/search/book.json")
    fun getBooksByName(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") secretKey: String,
        @Query("query") keyword: String,
        @Query("display") display: Int
    ): Call<SearchBookDto>

}