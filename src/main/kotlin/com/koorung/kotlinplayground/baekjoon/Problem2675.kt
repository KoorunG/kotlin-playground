package com.koorung.kotlinplayground.baekjoon

fun main() {val n = readln().toInt()
    repeat(n) {val (a, b) = readln().split(" ")
        println(buildString{b.forEach{s->repeat(a.toInt()){append(s)}}})}}