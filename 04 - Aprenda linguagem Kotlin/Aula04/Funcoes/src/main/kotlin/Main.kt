fun main(args: Array<String>) {
    val fullName = nomeCompleto("Lucas", "Inocencio")
    println(fullName)
    soma(10, 20)
    val resultado = dividir(50, 2)
    println(resultado)
}

fun nomeCompleto(nome: String, sobrenome: String): String {
    return "${nome} ${sobrenome}"
}

fun soma(num1: Int, num2: Int) {
    print("Resultado: ${num1 + num2}")
}

fun dividir(num1: Int, num2: Int): Int {
    return num1 / num2
}