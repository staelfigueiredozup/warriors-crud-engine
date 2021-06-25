package br.com.zup.warriors.engine.database.entity

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PastOrPresent

@Introspected
data class ConsoleEntity(
    @field:NotBlank
    var nome: String = "",
    @field:NotBlank
    var marca: String = "",
    @field:PastOrPresent
    var dataLancamento: LocalDate?,

    var id: UUID? = null,
    var dataCadastro: LocalDate = LocalDate.now()
)