package com.koorung.kotlinplayground.baekjoon


fun main() {
    val bf = System.`in`.bufferedReader()
    val deq = ArrayDeque<Int>()

    for(i in 1 .. bf.readLine().toInt()){
        deq.add(i)
    }

    while (true) {
        if (deq.size == 1) break
        deq.removeFirst()
        deq.addLast(deq.removeFirst())
    }
    println(deq[0])
}


//fun main() {
//    var n = 0
//    var i = readln().toInt()
//    while(true) {
//        if(i % 2 != 0) break
//        i /= 2
//        n++
//    }
//    val c = MutableList(i) { (it + 1) * 2.0.pow(n).toInt()}
//    while(true) {
//        println(c)
//        if(c.size == 1) {
//            println(c.first())
//            break
//        }
//        c.removeFirst()
//        c.add(c.removeFirst())
//    }
//}
