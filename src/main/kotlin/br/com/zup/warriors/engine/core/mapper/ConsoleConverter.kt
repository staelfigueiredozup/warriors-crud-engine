package br.com.zup.warriors.engine.core.mapper

import br.com.zup.warriors.engine.entrypoint.dto.ConsoleResponse
import br.com.zup.warriors.model.Console
import br.com.zup.warriors.engine.database.entity.ConsoleEntity

class ConsoleConverter {
    companion object {
        fun consoleToConsoleResponse(console: Console): ConsoleResponse {
            return ConsoleResponse(
                id = console.id,
                nome = console.nome,
                marca = console.marca,
                dataLancamento = console.dataLancamento.toString(),
                dataCadastro = console.dataCadastro.toString()
            )
        }

        fun consoleEntityToConsole(consoleEntity: ConsoleEntity): Console {
            return Console(
                id = consoleEntity.id,
                nome = consoleEntity.nome,
                marca = consoleEntity.marca,
                dataLancamento = consoleEntity.dataLancamento,
                dataCadastro = consoleEntity.dataCadastro
            )
        }

        fun listaConsolesToListaConsoleResponse(listaConsoles: List<Console>) =
            listaConsoles.map { console ->
                ConsoleResponse(console.id, console.nome, console.marca,
                    console.dataLancamento.toString(), console.dataCadastro.toString()) }

    }

}