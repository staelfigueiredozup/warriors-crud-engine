package br.com.zup.warriors.engine.core.service

import br.com.zup.warriors.engine.core.mapper.ConsoleConverter
import br.com.zup.warriors.engine.core.ports.ConsoleRepositoryPort
import br.com.zup.warriors.engine.core.ports.ConsoleServicePort
import br.com.zup.warriors.engine.entrypoint.dto.ConsoleResponse
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
import java.util.*
import javax.inject.Singleton

@Singleton
class ConsoleService(private val repository: ConsoleRepositoryPort): ConsoleServicePort {

    override fun consultaConsole(id: UUID): ConsoleResponse {
        val consoleResult = repository.findById(id)
            ?: throw ConsoleNaoEncontradoException("Console inexistente no banco de dados")
        return ConsoleConverter.consoleEntityToConsoleResponse(consoleResult)
    }

    override fun listaConsoles(): List<ConsoleResponse> {
        return repository.findAll().map { console ->
            ConsoleConverter.consoleEntityToConsoleResponse(console)
        }
    }

}