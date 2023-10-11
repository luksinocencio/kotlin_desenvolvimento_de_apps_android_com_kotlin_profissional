fun main(args: Array<String>) {
    val joao = Usuario("João Almeida", 1.70f)
    val bruno = Usuario("Bruno da Silva", 1.80f)

    println(joao.info())
    println(bruno.info())
}

// Construtor "()"
class Usuario(nome: String, altura: Float) {
    private val nome: String = nome
    private val altura: Float = altura

    fun info(): String {
        return "Usuário: $nome, Altura: $altura"
    }
}