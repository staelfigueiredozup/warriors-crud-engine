package br.com.zup.warriors.engine.service

import br.com.zup.warriors.engine.dto.ConsoleResponse
import java.util.*

interface ConsoleService {
    fun consultaConsole(id: UUID): ConsoleResponse
    fun listaConsoles(): List<ConsoleResponse>
}