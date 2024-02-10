package com.koorung.kotlinplayground.baekjoon

fun main()=print(if(readln().let { it == it.reversed() }) 1 else 0)