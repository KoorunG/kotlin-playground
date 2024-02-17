package com.koorung.kotlinplayground.baekjoon

fun main() {
    val (a,b)=readln().split(' ').map{it.toInt()}
    var l=minOf(a,b)
    while(a%l!=0||b%l!=0) {l--}
    print("$l\n${a*b/l}")
}