package com.example.camp_challenge_kakao_api.domain.usecase

import android.util.Log
import com.example.camp_challenge_kakao_api.data.repository.BookmarkRepository
import com.example.week_use_kakao_api.data.model.Document
import com.example.week_use_kakao_api.data.model.toDocument
import com.example.week_use_kakao_api.data.repository.SearchRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    val bookmarks = bookmarkRepository.bookmarks

    suspend fun onBookmarkChange(document: Document) {
        if(document.bookmarked) {
            bookmarkRepository.removeBookmark(document.url)
        } else {
            bookmarkRepository.addBookmark(document)
        }

        val temp = _documents.value.toMutableList()
        val tempIndex = temp.indexOf(document)
        temp[tempIndex] = document.copy(bookmarked = !document.bookmarked)
        _documents.value = temp
    }

    suspend fun search(query: String) {
        val temp = mutableListOf<Document>()
        runCatching {
            with(searchRepository) {
                getSearchImage(query).documents.forEach { temp.add(it.toDocument()) }
                getSearchVideo(query).documents.forEach { temp.add(it.toDocument()) }
            }
        }.onSuccess {
            Log.i(TAG, "search: onSuccess called")
            temp.sort()
            bookmarkInit(temp)
            _documents.value = temp
        }.onFailure {
            Log.i(TAG, "search: onFailure: $it")
        }
    }

    private fun bookmarkInit(itemList: MutableList<Document>) {
        for(i in 0 until itemList.size) {
            val current = itemList[i]
            val bookmarkList = bookmarks.value.bookmarkList
            if(bookmarkList.any { current.url == it.url }) {
                itemList[i] = current.copy(bookmarked = true)
            }
        }
    }
}
