package br.com.zup.warriors.engine.core.ports

import br.com.zup.warriors.engine.entrypoint.dto.ConsoleResponse
import java.util.*
import javax.inject.Singleton

@Singleton
interface ConsoleServicePort {
    fun consultaConsole(id: UUID): ConsoleResponse
    fun listaConsoles(): List<ConsoleResponse>
}