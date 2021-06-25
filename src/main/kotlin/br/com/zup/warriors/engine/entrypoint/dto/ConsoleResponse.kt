package br.com.zup.warriors.engine.entrypoint.dto

import java.util.*

data class ConsoleResponse(
    val id: UUID? = null,
    val nome: String = "",
    val marca: String = "",
    val dataLancamento: String?,
    val dataCadastro: String
)