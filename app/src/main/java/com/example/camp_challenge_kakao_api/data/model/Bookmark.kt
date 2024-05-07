package com.example.camp_challenge_kakao_api.data.model

import java.time.LocalDateTime

data class Bookmark(
    val url: String,
    val dateTime: LocalDateTime,
    val imageUrl: String
)
