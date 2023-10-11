fun main(args: Array<String>) {
    val num1 = 30
    val num2 = 20

    val resultado = operacao(num1, num2)

    println(resultado)
}

fun operacao(num1: Int, num2: Int): String {
    return if (num1 == num2) {
        "${num1} == ${num2}"
    } else if (num1 < num2) {
        "${num1} < ${num2}"
    } else {
         "${num1} > ${num2}"
    }
}