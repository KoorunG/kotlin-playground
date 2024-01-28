import java.util.*

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt() // 체스판의 세로 길이
    val m = sc.nextInt() // 체스판의 가로 길이
    sc.nextLine() // 개행문자 제거
    val board = Array(n) { sc.nextLine() } // 체스판의 상태
    var min = Int.MAX_VALUE // 다시 칠하는 최소 횟수

    // 8x8 크기로 잘라볼 수 있는 모든 경우에 대해 반복
    for (i in 0..n - 8) {
        for (j in 0..m - 8) {
            // 첫 번째 칸이 W인 경우와 B인 경우로 나누어서 카운트
            var countW = 0 // W로 시작하는 경우
            var countB = 0 // B로 시작하는 경우
            for (k in i until i + 8) {
                for (l in j until j + 8) {
                    // 행과 열의 인덱스 합이 짝수면 첫 번째 칸과 색이 같아야 함
                    if ((k + l) % 2 == 0) {
                        if (board[k][l] != 'W') countW++
                        if (board[k][l] != 'B') countB++
                    } else { // 행과 열의 인덱스 합이 홀수면 첫 번째 칸과 색이 다르게 함
                        if (board[k][l] != 'B') countW++
                        if (board[k][l] != 'W') countB++
                    }
                }
            }
            // 두 경우 중 작은 값으로 최솟값 갱신
            min = minOf(min, countW, countB)
        }
    }
    // 결과 출력
    println(min)
}
