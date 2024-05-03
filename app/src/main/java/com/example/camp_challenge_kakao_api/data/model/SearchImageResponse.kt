package com.example.week_use_kakao_api.data.model

import java.time.LocalDateTime

/**
 *
 * JSON -> data class converting 시에 data class의 parent class의
 * constructor가 호출되지 않는다. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * */


data class SearchImageResponse(
    val documents: List<ImageResponse>,
    val meta: Meta,
)

data class SearchVideoResponse(
    val documents: List<VideoResponse>,
    val meta: Meta,
)

data class ImageResponse(
    val collection: String,
    val datetime: String,
    val display_sitename: String,
    val doc_url: String,
    val image_url: String,
    val thumbnail_url: String,
    val height: Int,
    val width: Int,
)

data class VideoResponse(
    val author: String,
    val datetime: String,
    val play_time: Int,
    val thumbnail: String,
    val title: String,
    val url: String,
)

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int,
)


/**
 *
 *  Response Format: Image
 *         {
 *             "collection": "news",
 *             "datetime": "2015-12-11T21:13:30.000+09:00",
 *             "display_sitename": "텐아시아",
 *             "doc_url": "http://v.media.daum.net/v/20151211211330856",  -> 고유값으로 활용
 *             "height": 640,
 *             "image_url": "http://t1.daumcdn.net/news/201512/11/10asia/20151211211330191kqtg.jpg",
 *             "thumbnail_url": "https://search3.kakaocdn.net/argon/130x130_85_c/Ks9THy0WTjD",
 *             "width": 540
 *         },
 *
 *  Response Format: Meta
 *          {
 *              "is_end": false,
 *              "pageable_count": 3975,
 *              "total_count": 1133670
 *          }
 *
 *
 *  Response Format: Video
 *         {
 *             "author": "코드스피츠",
 *             "datetime": "2024-04-12T13:02:09.000+09:00",
 *             "play_time": 5850,
 *             "thumbnail": "https://search1.kakaocdn.net/argon/138x78_80_pr/G08qmPG7UuP",
 *             "title": "코틀린 코루틴 #1 (1~5장)",
 *             "url": "http://www.youtube.com/watch?v=Z4l74GWqXo4" -> 고유값으로 활용
 *         },
 * */