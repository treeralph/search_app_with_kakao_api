package com.example.week_use_kakao_api.data.model

import com.example.camp_challenge_kakao_api.stringToLocalDateTime
import java.time.LocalDateTime

fun ImageResponse.toDocument(): Document {
    return Document(
        imageUrl = image_url,
        titleText = display_sitename,
        time = stringToLocalDateTime(datetime)
    )
}

fun VideoResponse.toDocument(): Document {
    return Document(
        imageUrl = thumbnail,
        titleText = title,
        time = stringToLocalDateTime(datetime)
    )
}

data class Document(
    val imageUrl: String,
    val titleText: String,
    val time: LocalDateTime,
    val bookmarked: Boolean = false,
): Comparable<Document> {
    override fun compareTo(other: Document): Int {
        return if(time > other.time) -1 else 1
    }
}
