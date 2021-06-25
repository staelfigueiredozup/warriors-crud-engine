package br.com.zup.warriors.engine.core.ports

import br.com.zup.warriors.model.Console
import java.util.*
import javax.inject.Singleton

@Singleton
interface ConsoleEntityServicePort {
    fun findById (id: UUID) : Console?
    fun findAll(): List<Console>
}
