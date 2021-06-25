package br.com.zup.warriors.model

import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PastOrPresent

data class Console(
    @field:NotBlank
    var nome: String = "",
    @field:NotBlank
    var marca: String = "",
    @field:PastOrPresent
    var dataLancamento: LocalDate?,

    var id: UUID? = null,
    var dataCadastro: LocalDate = LocalDate.now()
)