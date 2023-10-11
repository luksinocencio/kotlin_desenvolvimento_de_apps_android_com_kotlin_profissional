fun main(args: Array<String>) {
    val nomes = listOf("Lucas", "Pedro", "João", "José", "Marcos Felipe", "Gabriel", "Leonardo",  "Sergio")
    val pessoas = mutableListOf<String>("Lucas", "Pedro", "João", "José", "Marcos Felipe", "Gabriel", "Leonardo", "Sergio")

    pessoas.add("Andre")
    pessoas.add("Mariana")
//    pessoas.remove("Lucas")

    println(pessoas)
    val posicao = pessoas.indexOf("Gabriel")
    println(posicao)
}