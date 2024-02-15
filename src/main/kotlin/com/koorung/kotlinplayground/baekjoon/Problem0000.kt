package com.koorung.kotlinplayground.baekjoon


fun main() {
    val mutableListOf = mutableListOf(0, 10, 3, 123124, 4325, 1, -1)
     mutableListOf.customSwap(0, 4)
    println(mutableListOf)

    println(bubbleSort(mutableListOf))
//    println(mutableListOf)
}

fun <T> List<T>.customSwap(i: Int, j: Int): List<T> {
    val list = this.toMutableList()
    list[i].also {
        list[i] = list[j]
        list[j] = it
    }
    return list.toList()
}

fun bubbleSort(original: MutableList<Int>): List<Int> {
    var arr = ArrayList<Int>(original).toList()
    var swap = true
    while(swap) {
        swap = false
        for(i in 0 until arr.size - 1) {
            if(arr[i] > arr[i + 1]) {
                arr = arr.customSwap(i, i + 1)
                swap = true
            }
        }
    }
    return arr
}