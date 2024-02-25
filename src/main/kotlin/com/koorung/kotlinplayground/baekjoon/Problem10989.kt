package com.koorung.kotlinplayground.baekjoon

fun main(){val s=IntArray(10_001)
    System.`in`.bufferedReader().use{for(i in 0 until it.readLine().toInt()) s[it.readLine().toInt()]++}
    System.out.bufferedWriter().use{for(i in s.indices) it.append("$i\n".repeat(s[i]))}}