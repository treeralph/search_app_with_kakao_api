package com.example.week_use_kakao_api.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.camp_challenge_kakao_api.BOOKMARKS_KEY
import com.example.camp_challenge_kakao_api.URGENT_TAG
import com.example.camp_challenge_kakao_api.data.local.BookmarkLocalDataSource
import com.example.camp_challenge_kakao_api.data.model.Bookmark
import com.example.camp_challenge_kakao_api.data.model.Bookmarks
import com.example.camp_challenge_kakao_api.data.repository.BookmarkRepository
import com.example.camp_challenge_kakao_api.domain.usecase.DocumentUseCase
import com.example.week_use_kakao_api.data.model.Document
import com.example.week_use_kakao_api.data.model.toDocument
import com.example.week_use_kakao_api.data.repository.SearchRepository
import com.example.week_use_kakao_api.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val documentUseCase: DocumentUseCase,
): ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _documents = MutableStateFlow(listOf<Document>())
    val documents = _documents.asStateFlow()

    private val _bookmarks = MutableStateFlow(Bookmarks())
    val bookmarks = _bookmarks.asStateFlow()

    private val _document = MutableStateFlow(Document())
    val document = _document.asStateFlow()

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
        Log.e(URGENT_TAG, "setDocument: num _bookmarks: ${_bookmarks.value.bookmarks.size}", )
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            documentUseCase.search(query)
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
