package com.koorung.kotlinplayground.baekjoon

import kotlin.math.roundToInt
fun main() {
    val n=readln().toInt()
    val k=(n*0.15).roundToInt()
    val l = List(n) { readln().toInt() }
    print(if(n==0) 0 else if(n<=3) l.average().roundToInt() else {l.sortedDescending().subList(k, n - k).average().roundToInt()})
}