fun main(args: Array<String>) {
    val resultado = compararNumeros(30, 20)
    println(resultado)
}

fun compararNumeros(num1: Int, num2: Int): String {
    return when {
        num1 < num2 -> {
            "$num1 < $num2"
        }
        num1 > num2 -> {
            "$num1 > $num2"
        }
        else -> {
            "$num1 == $num2"
        }
    }
}