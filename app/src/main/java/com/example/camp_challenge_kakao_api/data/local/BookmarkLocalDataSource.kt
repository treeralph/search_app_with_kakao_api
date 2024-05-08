package com.example.camp_challenge_kakao_api.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.camp_challenge_kakao_api.SHARED_PREFERENCES
import com.example.camp_challenge_kakao_api.URGENT_TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class BookmarkLocalDataSource(
    private val context: Context,
    private val key: String,
    private val defValue: String,
) {
    private val preferences = context.getSharedPreferences(SHARED_PREFERENCES, 0)
    fun edit(target: String) {
        preferences.edit().putString(key, target).apply()
    }
    fun get(): String {
        return preferences.getString(key, defValue) ?: defValue
    }
}