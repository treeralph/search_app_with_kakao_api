package com.example.camp_challenge_kakao_api.data.model

data class Bookmarks(
    val bookmarks: List<Bookmark>
) { constructor(): this(listOf()) }
