package br.com.matheuspadilha

import br.com.matheuspadilha.models.Gamer
import br.com.matheuspadilha.models.Jogo
import br.com.matheuspadilha.services.ConsumoApi
import br.com.matheuspadilha.utils.transformarEmIdade
import java.util.*

fun main(args: Array<String>) {
    val leitura = Scanner(System.`in`)
    val gamer = Gamer.criarGamer(leitura)

    println("Cadastro concluido com sucesso. Dados do gamer: ")
    println(gamer)

    println("Idade do gamer: ${gamer.dataNascimento?.transformarEmIdade()}")

    do {
        println("Digite o código de jogo para buscar: ")

        val busca = leitura.nextLine()

        val buscaApi = ConsumoApi()
        val informacaoJogo = buscaApi.buscaJogo(busca)

        var meuJogo: Jogo? = null

        val resultado = runCatching {
            meuJogo = Jogo(
                informacaoJogo.info.title,
                informacaoJogo.info.thumb
            )
        }

        resultado.onFailure {
            println("Jogo inexistente. Tente outro id.")
        }

        resultado.onSuccess {
            println("Deseja inserer uma descrição personalizada? S/N")
            val opcao = leitura.nextLine()
            if (opcao.equals("S", true)) {
                println("Insira a descrição personalizada para o jogo: ")
                val descricaoPersonalizada = leitura.nextLine()
                meuJogo?.descricao = descricaoPersonalizada
            } else {
                meuJogo?.descricao = meuJogo?.titulo
            }

            gamer.jogosBuuscados.add(meuJogo)
        }

        println("Deseja buscar um novo jogo? S/N")
        val resposta = leitura.nextLine()

    } while (resposta.equals("s", true))


    println("Jogos buscados: ")
    println(gamer.jogosBuuscados)

    println("\n")
    println("Jogos ordenados por titulo: ")
    gamer.jogosBuuscados.sortBy {
        it?.titulo
    }

    gamer.jogosBuuscados.forEach {
        println("Titulo: ${it?.titulo}")
    }

    println("\n")
    val jogosFiltrados = gamer.jogosBuuscados.filter {
        it?.titulo?.contains("Batman", true) ?: false
    }
    println("Jogos filtrados: ")
    println(jogosFiltrados)

    println("\n")
    println("Deseja excluir algum jogo da lista original? S/N")
    val opcao = leitura.nextLine()
    if (opcao.equals("S", true)) {
        println(gamer.jogosBuuscados)
        println("\n")
        println("Informe a posição do jogo que deseja excluir: ")
        val posicao = leitura.nextInt()
        gamer.jogosBuuscados.removeAt(posicao)
    }

    println("\n")
    println("Lista atualizada: ")
    println(gamer.jogosBuuscados)

    println("Busca finalizada com sucesso.")





    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}