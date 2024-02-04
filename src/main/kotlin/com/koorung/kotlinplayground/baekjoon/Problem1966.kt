package com.koorung.kotlinplayground.baekjoon

fun main() {
    repeat(readln().toInt()) {
        val (n, m) = readln().split(" ").map { it.toInt() }
        val l = IntArray(n)
        readln().split(" ").forEachIndexed { index, s -> l[index] = s.toInt() }
        val p = l.mapIndexed { index, v -> index to v }.toMutableList()
        val r = mutableListOf<Pair<Int,Int>>()
        while(true) {
            if(p.isEmpty()) {
                println(r.indexOfFirst { it.first == m } + 1)
                break
            }
            val max = p.maxOf { it.second }
            if(max != p.first().second) {
                p.add(p.removeFirst())
            } else {
                r.add(p.removeFirst())
            }
        }
    }
}