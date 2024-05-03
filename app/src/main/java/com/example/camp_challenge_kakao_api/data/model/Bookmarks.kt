package com.example.camp_challenge_kakao_api.data.model

data class Bookmarks(
    val size: Int,
    val bookmarkList: List<Bookmark>,
) { constructor(): this(0, listOf()) }
