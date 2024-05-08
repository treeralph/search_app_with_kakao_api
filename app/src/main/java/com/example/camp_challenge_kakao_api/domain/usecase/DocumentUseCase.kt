package com.example.camp_challenge_kakao_api.domain.usecase

import android.util.Log
import com.example.camp_challenge_kakao_api.data.model.Bookmarks
import com.example.camp_challenge_kakao_api.data.repository.BookmarkRepository
import com.example.week_use_kakao_api.data.model.Document
import com.example.week_use_kakao_api.data.model.toDocument
import com.example.week_use_kakao_api.data.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DocumentUseCase(
    private val searchRepository: SearchRepository,
    private val bookmarkRepository: BookmarkRepository
) {
    companion object {
        private const val TAG = "DocumentUseCase"
    }

    private val _documents = MutableStateFlow(listOf<Document>())
    val documents = _documents.asStateFlow()

    val bookmarks = bookmarkRepository.get()

    fun editBookmarks(bookmarks: Bookmarks) = bookmarkRepository.edit(bookmarks)

    suspend fun search(query: String) {
        val temp = mutableListOf<Document>()
        runCatching {
            with(searchRepository) {
                getSearchImage(query).documents.forEach { imageResponse ->
                    temp.add(
                        imageResponse.toDocument(
                            bookmarks.bookmarks.any { it.url == imageResponse.doc_url }
                        )
                    )
                }
                getSearchVideo(query).documents.forEach { videoResponse ->
                    temp.add(
                        videoResponse.toDocument(
                            bookmarks.bookmarks.any { it.url == videoResponse.url }
                        )
                    )
                }
            }
        }.onSuccess {
            Log.i(TAG, "search: onSuccess called")
            temp.sort()
            _documents.value = temp
        }.onFailure {
            Log.e(TAG, "search: onFailure: $it")
        }
    }
}