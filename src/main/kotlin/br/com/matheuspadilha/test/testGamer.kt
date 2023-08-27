package br.com.matheuspadilha.test

import br.com.matheuspadilha.models.Gamer

fun main() {
    val gamer1 = Gamer("Matheus", "matheus@email.com")

    val gamer2 = Gamer("Padilha", "padilha@email.com", "01/01/1000", "padilha")


    gamer1.let {
        it.dataNascimento = "01/01/1000"
        it.usuario = "padilhom"
    }

    println(gamer1.toString())
    println(gamer2.toString())
}