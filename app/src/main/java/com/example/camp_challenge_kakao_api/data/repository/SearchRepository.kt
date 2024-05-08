package com.example.week_use_kakao_api.data.repository

import androidx.annotation.IntRange
import com.example.week_use_kakao_api.data.remote.SearchRemoteDataSource

class SearchRepository(
    private val dataSource: SearchRemoteDataSource
) {
    suspend fun getSearchImage(
        query: String,
        sort: String = "accuracy",
        @IntRange(from = 1, to = 50) page: Int = 1,
        @IntRange(from = 1, to = 80) size: Int = 80
    ) = dataSource.getSearchImage(query, sort, page, size)

    suspend fun getSearchVideo(
        query: String,
        sort: String = "accuracy",
        @IntRange(from = 1, to = 15) page: Int = 1,
        @IntRange(from = 1, to = 30) size: Int = 15
    ) = dataSource.getSearchVideo(query, sort, page, size)
}