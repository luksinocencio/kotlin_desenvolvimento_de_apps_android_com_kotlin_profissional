fun main(args: Array<String>) {
    var pessoas = listOf("Marcelo", "João", "Lucas", "Jose", "Felipe")
//    pessoas.forEach {
//        println(it)
//    }

    pessoas.forEach { pessoa ->
        println(pessoa)
    }

    val filtro = pessoas.filter { it.startsWith('M', true) }
    filtro.forEachIndexed { index, pessoa ->
        println("Posição: $index Nome: $pessoa")
    }

    val filtro2 = pessoas.filter { it.endsWith('e', true) }
    filtro2.forEachIndexed { index, pessoa ->
        println("Posição: $index Nome: $pessoa")
    }

//    pessoas.forEachIndexed { index, pessoa ->
//        println("Posição: $index Nome: $pessoa")
//    }


}