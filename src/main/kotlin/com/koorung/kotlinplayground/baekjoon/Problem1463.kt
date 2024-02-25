package com.koorung.kotlinplayground.baekjoon

fun main() {
    val n=readln().toInt()
    val s=IntArray(1_000_001)
    for(i in 2 .. n) {
        s[i]=s[i-1]+1
        if(i%2==0)s[i]=minOf(s[i],s[i/2]+1)
        if(i%3==0)s[i]=minOf(s[i],s[i/3]+1)
    }
    print(s[n])
}