package com.example.camp_challenge_kakao_api.data.model

import com.example.camp_challenge_kakao_api.stringToLocalDateTime
import java.time.LocalDateTime

fun ImageResponse.toDocument(bookmarked: Boolean): Document {
    return Document(
        url = doc_url,
        imageUrl = image_url,
        titleText = display_sitename,
        time = stringToLocalDateTime(datetime),
        bookmarked = bookmarked
    )
}

fun VideoResponse.toDocument(bookmarked: Boolean): Document {
    return Document(
        url = url,
        imageUrl = thumbnail,
        titleText = title,
        time = stringToLocalDateTime(datetime),
        bookmarked = bookmarked
    )
}

data class Document(
    val url: String,
    val imageUrl: String,
    val titleText: String,
    val time: LocalDateTime,
    val bookmarked: Boolean = false,
): Comparable<Document> {
    constructor(): this("", "", "", LocalDateTime.now())
    override fun compareTo(other: Document): Int {
        return if(time > other.time) -1 else 1
    }
}
