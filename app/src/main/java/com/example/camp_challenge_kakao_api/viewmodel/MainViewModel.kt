package com.example.week_use_kakao_api.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.camp_challenge_kakao_api.data.repository.BookmarkRepository
import com.example.camp_challenge_kakao_api.domain.usecase.DocumentUseCase
import com.example.week_use_kakao_api.data.model.Document
import com.example.week_use_kakao_api.data.model.toDocument
import com.example.week_use_kakao_api.data.repository.SearchRepository
import com.example.week_use_kakao_api.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val documentUseCase: DocumentUseCase,
) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    val documents = documentUseCase.documents
    val bookmarks = documentUseCase.bookmarks

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            documentUseCase.search(query)
        }
    }
    suspend fun onBookmarkChange(document: Document) = documentUseCase.onBookmarkChange(document)
}

/**
 * https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories?hl=ko
 * CreationExtras가 포함된 ViewModel 사용법
 * */
class MainViewModelFactory(
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            DocumentUseCase(
                SearchRepository(RetrofitClient.search),
                BookmarkRepository(context)
            )
        ) as T
    }
}