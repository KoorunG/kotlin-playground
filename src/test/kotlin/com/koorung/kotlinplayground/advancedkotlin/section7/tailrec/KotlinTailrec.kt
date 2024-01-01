package com.koorung.kotlinplayground.advancedkotlin.section7.tailrec


// 일반 재귀
fun factorialV1(n: Int): Int =
    if (n == 1) 1 else n * factorialV1(n - 1);

// 꼬리 재귀
// 스택이 쌓이는 재귀함수 호출을 루프로 변경시켜줌
// (일반 재귀함수는 지속적으로 함수 call이 발생하여 스택메모리에 쌓이지만 꼬리재귀는 루프로 변경하여 최적화시킨다)
tailrec fun factorialV2(n: Int, acc: Int = 1): Int =
    if (n <= 1) acc else factorialV2(n - 1, n * acc)