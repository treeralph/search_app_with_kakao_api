package com.example.camp_challenge_kakao_api.data.repository

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.preferencesDataStore
import com.example.camp_challenge_kakao_api.data.local.BookmarkLocalDataSource
import com.example.week_use_kakao_api.data.model.Document
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("")
class BookmarkRepository(
    private val source: BookmarkLocalDataSource,
    private val context: Context,
) {

    val bookmarks: Flow<Unit> = context.dataStore.data.map {

    }
}