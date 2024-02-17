package com.koorung.kotlinplayground.baekjoon

fun main() {
    val (n, k) = readln().split(' ').map { it.toInt() }
    val list = CircularLinkedList<Int>()
    repeat(n) {
        list.append(it + 1)
    }
    val l = mutableListOf<Int>()
    var curr = list.get(k)
    l.add(curr.data)
    while(l.size != n) {
        l.add(curr.getNext(k).data)
        curr = curr.getNext(k)
    }
    println(l)
}

data class Node<T>(
    var data: T,
    var next: Node<T>? = null
) {
    fun getNext(acc: Int): Node<T> {
        var curr = next
        for(i in 0 until acc - 1) {
            curr = curr!!.next
        }
        return curr!!
    }
}

class CircularLinkedList<T> {
    private var head: Node<T>? = null

    fun get(data: T): Node<T> {
        var curr = head
        while(curr != null) {
            curr = curr.next
            if(curr!!.data == data) break
        }
        return curr!!
    }

    fun append(data: T) {
        if (head == null) {
            head = Node(data)
            head!!.next = head
            return
        }

        var curr = head
        while (curr!!.next != head) {
            curr = curr.next
        }

        val next = Node(data)
        curr.next = next
        next.next = head
    }
}