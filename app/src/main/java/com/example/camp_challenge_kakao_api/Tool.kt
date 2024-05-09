package com.example.camp_challenge_kakao_api

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun stringToLocalDateTime(target: String): LocalDateTime {
    // 2019-08-12T18:00:00.000+09:00
    val timeFormatter = DateTimeFormatter.ISO_DATE_TIME
    return LocalDateTime.from(timeFormatter.parse(target))
}
