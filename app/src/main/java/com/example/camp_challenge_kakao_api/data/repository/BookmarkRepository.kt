package com.example.camp_challenge_kakao_api.data.repository

import android.util.Log
import com.example.camp_challenge_kakao_api.URGENT_TAG
import com.example.camp_challenge_kakao_api.data.local.BookmarkLocalDataSource
import com.example.camp_challenge_kakao_api.data.model.Bookmark
import com.example.camp_challenge_kakao_api.data.model.Bookmarks
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 * 일단 다른 부분은 신경쓰지 말고 작성하자,
 * */
@OptIn(DelicateCoroutinesApi::class)
class BookmarkRepository(
    private val source: BookmarkLocalDataSource,
) {
    private val gson = Gson()

    private val _bookmarks = MutableStateFlow(Bookmarks())
    val bookmarks = _bookmarks.asStateFlow()

    // 바보냐 ?

    // todo: bookmark 추가 안되는 문제 -> job 종료 되는 듯하다.
    init {
//        CoroutineScope(Dispatchers.Default).launch {
//            source.element
//                .collect { elem ->
//                    Log.i(URGENT_TAG, ":$elem ")
//                    _bookmarks.value = gson.fromJson(elem, Bookmarks::class.java)
//                }
//        }

        GlobalScope.launch {
            source.element
                .collect { elem ->
                    Log.i(URGENT_TAG, ":$elem ")
                    _bookmarks.value = gson.fromJson(elem, Bookmarks::class.java)
                }
        }
    }

    fun edit(bookmarks: Bookmarks) = source.edit(gson.toJson(bookmarks))
}