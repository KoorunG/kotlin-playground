package com.koorung.kotlinplayground.baekjoon

import java.util.*

fun main() {
    val s = Stack<Int>()
    val sb = StringBuilder()
    var c = 1
    for (i in List(readln().toInt()) { readln().toInt() }) {
        while(c <= i) {
            s.push(c++)
            sb.append("+\n")
        }
        if(s.peek() == i) {
            s.pop()
            sb.append("-\n")
        }else{
            print("NO")
            return
        }
    }
    print(sb)
}












