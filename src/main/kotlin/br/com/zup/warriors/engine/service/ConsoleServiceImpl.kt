package br.com.zup.warriors.engine.service

import br.com.zup.warriors.engine.dto.ConsoleResponse
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
import br.com.zup.warriors.repository.ConsoleRepository
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Singleton

@Validated
@Singleton
class ConsoleServiceImpl(private var repository: ConsoleRepository) : ConsoleService {

    override fun consultaConsole(id: UUID): ConsoleResponse {
        val possivelConsole = repository.findById(id)
            ?: throw ConsoleNaoEncontradoException("Console inexistente no banco de dados")
        return possivelConsole.toDto()
    }

    override fun listaConsoles(): List<ConsoleResponse> {
        return repository.findAll().map { console ->
            console.toDto()
        }
    }
}