package br.com.zup.warriors.engine.core.mapper

import br.com.zup.warriors.engine.database.entity.ConsoleEntity
import br.com.zup.warriors.engine.entrypoint.dto.ConsoleResponse
import br.com.zup.warriors.model.Console

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

        fun consoleEntityToConsoleResponse(consoleEntity: ConsoleEntity): ConsoleResponse {
            return ConsoleResponse(
                id = consoleEntity.id,
                nome = consoleEntity.nome,
                marca = consoleEntity.marca,
                dataLancamento = consoleEntity.dataLancamento.toString(),
                dataCadastro = consoleEntity.dataCadastro.toString()
            )
        }

        fun listaConsolesToListaConsoleResponse(listaConsoles: List<Console>) =
            listaConsoles.map { console ->
                ConsoleResponse(console.id, console.nome, console.marca,
                    console.dataLancamento.toString(), console.dataCadastro.toString()) }

    }

}