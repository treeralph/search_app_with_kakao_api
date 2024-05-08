package com.example.camp_challenge_kakao_api.domain.usecase

import android.util.Log
import com.example.camp_challenge_kakao_api.FIRST_SEARCH
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

    suspend fun search(
        query: String,
        page: Int = 1,
        flag: String = FIRST_SEARCH
    ) {
        val temp = mutableListOf<Document>()
        runCatching {
            with(searchRepository) {
                getSearchImage(
                    query = query,
                    page = page
                ).documents.forEach { imageResponse ->
                    temp.add(
                        imageResponse.toDocument(
                            bookmarks.bookmarks.any { it.url == imageResponse.doc_url }
                        )
                    )
                }
                getSearchVideo(
                    query = query,
                    page = page
                ).documents.forEach { videoResponse ->
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
            if(flag == FIRST_SEARCH) {
                _documents.value = temp
            } else {
                val temp2 = _documents.value.toMutableList()
                temp2.addAll(temp)
                _documents.value = temp2
            }
        }.onFailure {
            Log.e(TAG, "search: onFailure: $it")
        }
    }


}