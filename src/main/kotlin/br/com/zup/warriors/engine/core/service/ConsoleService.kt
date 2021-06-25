package br.com.zup.warriors.engine.core.service

import br.com.zup.warriors.engine.core.mapper.ConsoleConverter
import br.com.zup.warriors.engine.core.ports.ConsoleEntityServicePort
import br.com.zup.warriors.engine.core.ports.ConsoleServicePort
import br.com.zup.warriors.engine.entrypoint.dto.ConsoleResponse
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
import java.util.*
import javax.inject.Singleton

@Singleton
class ConsoleService(private val service: ConsoleEntityServicePort): ConsoleServicePort {

    override fun consultaConsole(id: UUID): ConsoleResponse {
        val consoleResult = service.findById(id)
            ?: throw ConsoleNaoEncontradoException("Console inexistente no banco de dados")
        return ConsoleConverter.consoleToConsoleResponse(consoleResult)
    }

    override fun listaConsoles(): List<ConsoleResponse> {
        return service.findAll().map { console ->
            ConsoleConverter.consoleToConsoleResponse(console)
        }
    }

}