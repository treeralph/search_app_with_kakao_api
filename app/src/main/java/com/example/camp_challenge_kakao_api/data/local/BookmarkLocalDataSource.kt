package com.example.camp_challenge_kakao_api.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore


/**
 * Datasotre 말고 sharedPreferences로 가자
 * */
class BookmarkLocalDataSource {
    companion object {
        const val BOOKMARK_PREFERENCE_NAME = "BOOKMARK"
    }
}