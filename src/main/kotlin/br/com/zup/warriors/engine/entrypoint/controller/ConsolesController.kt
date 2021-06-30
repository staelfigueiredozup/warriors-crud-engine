package br.com.zup.warriors.engine.entrypoint.controller

import br.com.zup.warriors.engine.core.mapper.ConsoleConverter
import br.com.zup.warriors.engine.core.ports.ConsoleServicePort
import br.com.zup.warriors.engine.entrypoint.dto.ConsoleResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import java.util.*

@Controller("/v1/consoles")
class ConsolesController(private val service: ConsoleServicePort) {

    @Get("/{id}")
    fun consultaConsole(@PathVariable id: String) : HttpResponse<Any> {
        val responseConsultaId = try {

            ConsoleConverter.consoleToConsoleResponse(service.consultaConsole(UUID.fromString(id)))

        } catch (e: RuntimeException) {
            if(e.message == "Console inexistente no banco de dados"){
                return HttpResponse.notFound(HttpStatus.NOT_FOUND).body(e.message)
            } else
                return HttpResponse.serverError(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
        return HttpResponse.ok(HttpStatus.OK).body(responseConsultaId)
    }

    @Get
    fun listaConsoles(): HttpResponse<List<ConsoleResponse>>{
        val responseConsultaTodos = ConsoleConverter.listaConsolesToListaConsoleResponse(service.listaConsoles())
        return HttpResponse.ok(HttpStatus.OK).body(responseConsultaTodos)
    }

}