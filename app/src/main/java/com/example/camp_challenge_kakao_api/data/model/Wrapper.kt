package com.example.week_use_kakao_api.data.model

import com.example.camp_challenge_kakao_api.stringToLocalDateTime
import java.time.LocalDateTime

fun ImageResponse.toDocument(): Document {
    return Document(
        imageUrl = image_url,
        url = doc_url,
        titleText = display_sitename,
        time = stringToLocalDateTime(datetime)
    )
}

fun VideoResponse.toDocument(): Document {
    return Document(
        imageUrl = thumbnail,
        url = url,
        titleText = title,
        time = stringToLocalDateTime(datetime)
    )
}



/**
 * 최초에 데이터를 가져와서 Documnet로 converting 하면
 * bookmark 정보를 가지고 와서 체크해 주어야 한다.
 * toDocumnet에서 처리해주자.
 *
 * */
data class Document(
    val imageUrl: String,
    val url: String,
    val titleText: String,
    val time: LocalDateTime,
    val bookmarked: Boolean = false,
): Comparable<Document> {
    override fun compareTo(other: Document): Int {
        return if(time > other.time) -1 else 1
    }
}
