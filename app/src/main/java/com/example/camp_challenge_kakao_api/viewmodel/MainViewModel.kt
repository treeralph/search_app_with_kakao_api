package com.example.camp_challenge_kakao_api.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.camp_challenge_kakao_api.BOOKMARKS_KEY
import com.example.camp_challenge_kakao_api.FIRST_SEARCH
import com.example.camp_challenge_kakao_api.data.local.BookmarkLocalDataSource
import com.example.camp_challenge_kakao_api.data.model.Bookmark
import com.example.camp_challenge_kakao_api.data.model.Bookmarks
import com.example.camp_challenge_kakao_api.data.repository.BookmarkRepository
import com.example.camp_challenge_kakao_api.domain.usecase.DocumentUseCase
import com.example.camp_challenge_kakao_api.data.model.Document
import com.example.camp_challenge_kakao_api.data.repository.SearchRepository
import com.example.camp_challenge_kakao_api.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val documentUseCase: DocumentUseCase,
): ViewModel() {

    private val _documents = MutableStateFlow(listOf<Document>())
    val documents = _documents.asStateFlow()

    private val _bookmarks = MutableStateFlow(Bookmarks())
    val bookmarks = _bookmarks.asStateFlow()

    private val _document = MutableStateFlow(Document())
    val document = _document.asStateFlow()

    private var currentQuery = ""
    private var currentPage = 1

    init {
        viewModelScope.launch {
            documentUseCase.documents.collect {
                _documents.value = it
            }
        }

        _bookmarks.value = documentUseCase.bookmarks
    }

    fun setDocument(document: Document) {
        val edited = document.copy(bookmarked = !document.bookmarked)

        _document.value = edited

        val temp = _bookmarks.value.bookmarks.toMutableList()
        if(edited.bookmarked) {
            if(!temp.any { edited.url == it.url }) {
                temp.add(
                    Bookmark(
                        url = document.url,
                        dateTime = document.time,
                        imageUrl = document.imageUrl
                    )
                )
            }
        } else {
            if(temp.any { edited.url == it.url }) {
                temp.removeIf { it.url == edited.url }
            }
        }

        val temp2 = Bookmarks(bookmarks = temp)

        documentUseCase.editBookmarks(temp2)
        _bookmarks.value = temp2
    }

    fun setBookmark(bookmark: Bookmark) {
        val index = _documents.value.indexOfFirst { it.url == bookmark.url }
        if(index != -1) {
            val previous = _documents.value[index]
            val current = previous.copy(bookmarked = false)
            _document.value = current
        }
        val temp = _bookmarks.value.bookmarks.toMutableList()
        temp.removeIf { it.url == bookmark.url }
        val temp2 = Bookmarks(bookmarks = temp)
        documentUseCase.editBookmarks(temp2)
        _bookmarks.value = temp2
    }

    @Synchronized
    fun search(
        query: String = currentQuery,
        page: Int = currentPage,
        flag: String = FIRST_SEARCH
    ) {
        if(query != currentQuery) currentPage = 1
        currentQuery = query
        viewModelScope.launch(Dispatchers.IO) {
            documentUseCase.search(
                query = query,
                page = page,
                flag = flag
            )
            currentPage++
        }
    }
}

class MainViewModelFactory(
    private val context: Context
): ViewModelProvider.Factory {
    private val gson = Gson()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            DocumentUseCase(
                SearchRepository(RetrofitClient.search),
                BookmarkRepository(
                    BookmarkLocalDataSource(
                        context,
                        BOOKMARKS_KEY,
                        gson.toJson(Bookmarks())
                    )
                )
            ),
        ) as T
    }
}
