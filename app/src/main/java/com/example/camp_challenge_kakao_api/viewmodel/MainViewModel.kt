package com.example.week_use_kakao_api.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.camp_challenge_kakao_api.BOOKMARKS_KEY
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val documentUseCase: DocumentUseCase,
    private val bookmarkRepository: BookmarkRepository
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

        viewModelScope.launch {
            bookmarkRepository.bookmarks.collect {
                _bookmarks.value = it
            }
        }
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

        bookmarkRepository.edit(
            Bookmarks(
                bookmarks = temp
            )
        )
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            documentUseCase.search(query)
        }
    }

    fun bookmarkOnClick(document: Document) {
        /**
         * 1. _documents 의 해당 document bookmarked 를 수정한다.
         * 2. _bookmarks 의 해당 bookmark 를 추가하거나 삭제한다.
         * 3. SearchFragment 의 recyclerView 에 해당 item 의 변화를 notify 한다.
         * 4. BookmarksFragment 의 recyclerView에 해당 item 의 추가 혹은 삭제를 notify 한다.
         * */

        /** <Solution>
         * 1. 현재 SearchFragment 의 구조는 검색어에 해당하는 아이템들을 업데이트 하는 것에 포커스 되어 있다.
         *      따라서, _documents 를 통하여 본래 작업을 진행하고 _document StateFlow 를 추가로 만들어
         *      사용 하도록 하자. 이때, 주의할 점은 _documents 와 _document 의 sync 이다.
         *      이를 위하여 다음과 같은 작업을 진행해 줘야 한다.
         *      1-1. bookmark button click event 가 트리거 되면 _document 에 해당 값을 업데이트 한다.
         *      1-2. _documents 에 해당 값을 업데이트 한다. 이때, 본래 사용하던 리스트를 사용하는 것으로
         *              emit을 트리거 하지 않도록 주의한다.
         *      1-3. RecyclerView item 에 해당 값을 업데이트 한다.
         *      1-4. RecyclerView 의 해당 index 를 notify 해준다.
         *
         * 2. 1번의 solution 으로 _bookmark 를 새롭게 정의해 주는 것으로 수정 중인 것을 명시해 주도록 하였다.
         *      이 flow 에 수신자를 추가, bookmarked 를 분기로 하여 _bookmarks update를 수행하도록 한다.
         *      _bookmarks update 를 수행하게 되면 viewModel 의 _bookmarks 가
         *      local source -> repository -> viewmodel 의 순서로 업데이트가 트리거 된다.
         *
         * 3. 1-4에서 수행한다.
         * 4. 2에서 수행한다.
         * */

    }
}

class MainViewModelFactory(
    private val context: Context
): ViewModelProvider.Factory {
    private val gson = Gson()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            DocumentUseCase(SearchRepository(RetrofitClient.search)),
            BookmarkRepository(
                BookmarkLocalDataSource(
                    context,
                    BOOKMARKS_KEY,
                    gson.toJson(Bookmarks())
                )
            )
        ) as T
    }
}