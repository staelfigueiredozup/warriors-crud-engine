package br.com.zup.warriors.engine.core.ports

import br.com.zup.warriors.engine.database.entity.ConsoleEntity
import java.util.*
import javax.inject.Singleton

@Singleton
interface ConsoleRepositoryPort {
    fun findById(id: UUID): ConsoleEntity?
    fun findAll(): List<ConsoleEntity>
}