package br.com.zup.warriors.engine.core.ports

import br.com.zup.warriors.model.Console
import java.util.*
import javax.inject.Singleton

@Singleton
interface ConsoleServicePort {
    fun consultaConsole(id: UUID): Console
    fun listaConsoles(): List<Console>
}