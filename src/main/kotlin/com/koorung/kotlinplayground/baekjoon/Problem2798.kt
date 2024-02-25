package com.koorung.kotlinplayground.baekjoon

private fun f() = readln().split(" ").map{it.toInt()}
fun main() {
    val (_,m)=f()
    val c=f()
    val s=mutableSetOf<Int>()
    val z=c.size
    for(i in c.indices){for(j in i+1 until z){for(k in j+1 until z)if(c[i]+c[j]+c[k]<=m) s.add(c[i]+c[j]+c[k]-m)}}
    println(s.max()+m)
}
