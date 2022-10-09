package com.olympos.tripbook

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun StringDateToKor(){

        val givenDate = "2022-08-14"
        val expectString = "2022년 8월 14일"

        val dateArr = givenDate.split("-")
        println(dateArr)

        var result = ""

        for (i in 0..2) {
            val res = dateArr[i].toInt()

            when (i) {
                0 -> result += res.toString() + "년 "
                1 -> result += res.toString() + "월 "
                2 -> result += res.toString() + "일"
            }
        }

        assertEquals(expectString, result)
    }
}