package com.example.camp_challenge_kakao_api.data.usecase

import android.util.Log
import com.example.week_use_kakao_api.data.model.Document
import com.example.week_use_kakao_api.data.model.toDocument
import com.example.week_use_kakao_api.data.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DocumentUseCase(
    private val searchRepository: SearchRepository
) {
    companion object {
        private const val TAG = "DocumentUseCase"
    }

    private val _documents = MutableStateFlow(listOf<Document>())
    val documents = _documents.asStateFlow()

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
            _documents.value = temp
        }.onFailure {
            Log.i(TAG, "search: onFailure: $it")
        }
    }
}