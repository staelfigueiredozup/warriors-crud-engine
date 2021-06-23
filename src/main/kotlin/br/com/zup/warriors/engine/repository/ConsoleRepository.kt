package br.com.zup.warriors.repository

import br.com.zup.warriors.model.Console
import java.util.*
import javax.inject.Singleton

@Singleton
interface ConsoleRepository {

    fun findById (id: UUID) : Console?
    fun findAll(): List<Console>

}