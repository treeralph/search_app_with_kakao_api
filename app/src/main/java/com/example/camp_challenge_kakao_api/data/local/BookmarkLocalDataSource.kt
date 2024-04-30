package com.example.camp_challenge_kakao_api.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

class BookmarkLocalDataSource {
    companion object {
        const val BOOKMARK_PREFERENCE_NAME = "BOOKMARK"
    }
    val Context.dataStore by preferencesDataStore(
        name = BOOKMARK_PREFERENCE_NAME
    )
}