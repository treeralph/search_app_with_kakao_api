package com.example.camp_challenge_kakao_api

fun main() {

    val tempList = mutableListOf<Temp>()
    tempList.add(Temp1(1L, "A", 3))
    tempList.add(Temp1(2L, "B", 3))
    tempList.add(Temp1(3L, "C", 3))
    tempList.add(Temp2(4L, "D", "DD"))
    tempList.add(Temp2(5L, "E", "EE"))
    tempList.add(Temp2(6L, "F", "FF"))

    tempList.forEach {
        println(it.name2)
    }
}

abstract class Temp(
    val name2: String,
)

data class Temp1(
    val id: Long,
    val name: String,
    val age: Int,
): Temp(name)

data class Temp2(
    val id: Long,
    val name: String,
    val address: String
): Temp(name)