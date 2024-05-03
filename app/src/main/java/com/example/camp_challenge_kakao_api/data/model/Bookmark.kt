package com.example.camp_challenge_kakao_api.data.model

import java.time.LocalDateTime

data class Bookmark(
    val url: String, // 고유값으로 활용할 예정
    val dateTime: LocalDateTime,
    val imageUrl: String
)
