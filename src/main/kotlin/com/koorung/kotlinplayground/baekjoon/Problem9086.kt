package com.koorung.kotlinplayground.baekjoon

fun main()=repeat(readln().toInt()){println(readln().let{"${it.first()}${it.last()}"})}