package com.koorung.kotlinplayground.baekjoon

fun main()=print(List(readln().toInt()){readln().split(' ')}.sortedBy{it[0].toInt()}.joinToString("\n"){"${it[0]} ${it[1]}"})