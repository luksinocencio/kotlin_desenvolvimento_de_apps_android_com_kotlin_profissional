fun main(args: Array<String>) {
    var pessoas = listOf("Marcelo", "João", "Lucas", "Jose", "Felipe")

    val alguem = "Gabriel"

//    for (pessoa in pessoas) {
//        println(pessoa)
//    }
    val nameOrdering = pessoas.sortedBy { it.length }

    for ((posicao, pessoa) in nameOrdering.withIndex()) {
        println("Posicacao: $posicao Nome: $pessoa")
    }
}