package com.example.camp_challenge_kakao_api

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.gson.internal.bind.util.ISO8601Utils
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

fun stringToLocalDateTime(target: String): LocalDateTime {
    // 2019-08-12T18:00:00.000+09:00
    val timeFormatter = DateTimeFormatter.ISO_DATE_TIME
    return LocalDateTime.from(timeFormatter.parse(target))
}


//
//fun main() {
//    val a = "2019-08-12T18:00:00.000+09:00"
//    val b = stringToLocalDateTime(a)
//    println("" +
//            "${b.year}\n" +
//            "${b.monthValue}\n" +
//            "${b.dayOfMonth}\n" +
//            "${b.hour}\n" +
//            "${b.minute}\n" +
//            "${b.second}\n" +
//            "")
//}