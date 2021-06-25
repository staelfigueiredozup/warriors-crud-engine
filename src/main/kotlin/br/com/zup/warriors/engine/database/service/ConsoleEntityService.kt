package br.com.zup.warriors.engine.database.service

import br.com.zup.warriors.engine.core.mapper.ConsoleConverter
import br.com.zup.warriors.engine.core.ports.ConsoleEntityServicePort
import br.com.zup.warriors.engine.database.repository.ConsoleEntityRepository
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
import br.com.zup.warriors.model.Console
import java.util.*
import javax.inject.Singleton

@Singleton
class ConsoleEntityService(private var repository: ConsoleEntityRepository) : ConsoleEntityServicePort {

    override fun findById(id: UUID): Console? {
        val consoleResult = repository.findById(id)
            ?: throw ConsoleNaoEncontradoException("Console inexistente no banco de dados")
        return ConsoleConverter.consoleEntityToConsole(consoleResult)
    }

    override fun findAll(): List<Console> {
        return repository.findAll().map { console ->
            ConsoleConverter.consoleEntityToConsole(console)
        }
    }
}