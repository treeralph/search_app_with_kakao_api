package com.example.camp_challenge_kakao_api.data.repository

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.camp_challenge_kakao_api.BOOKMARK_SHARED_PREFERENCES
import com.example.camp_challenge_kakao_api.data.model.Bookmark
import com.example.camp_challenge_kakao_api.data.model.Bookmarks
import com.example.camp_challenge_kakao_api.data.repository.BookmarkRepository
import com.example.week_use_kakao_api.data.model.Document
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

/**
 * Datasotre 말고 sharedPreferences로 가자
 * */
class BookmarkRepository(
    context: Context
) {
    private val preferences = context.getSharedPreferences(BOOKMARK_SHARED_PREFERENCES, 0)
    private val bookmarksKey = "BOOKMARK"
    private val gson = Gson()
    private val defaultString = gson.toJson(Bookmarks())

    private val _bookmarks = MutableStateFlow(Bookmarks())
    val bookmarks = _bookmarks.asStateFlow()

    init { setPreferenceChangeListener() }

    // Document에 고유값으로 사용할 것 필요

    fun addBookmark(document: Document) {
        val temp1 = _bookmarks.value
        val temp2 = temp1.bookmarkList.toMutableList()
        temp2.add(
            Bookmark(document.url, document.time, document.imageUrl)
        )
        preferences.edit().putString(
            bookmarksKey,
            gson.toJson(
                Bookmarks(
                    size = temp1.size,
                    bookmarkList = temp2
                )
            )
        ).apply()
    }
    fun removeBookmark(url: String) {
        val temp1 = _bookmarks.value
        val temp2 = temp1.bookmarkList.toMutableList()
        temp2.removeIf { it.url == url}
        preferences.edit().putString(
            bookmarksKey,
            gson.toJson(
                Bookmarks(
                    size = temp1.size,
                    bookmarkList = temp2
                )
            )
        ).apply()
    }

    private fun setPreferenceChangeListener() {
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            _bookmarks.value =
                gson.fromJson(
                    sharedPreferences.getString(key, defaultString),
                    Bookmarks::class.java
                )
        }
    }
}