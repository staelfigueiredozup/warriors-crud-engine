package br.com.zup.warriors.engine.database.repository

import br.com.zup.warriors.engine.database.entity.ConsoleEntity
import java.util.*
import javax.inject.Singleton

@Singleton
interface ConsoleEntityRepository {
    fun findById(id: UUID): ConsoleEntity?
    fun findAll(): List<ConsoleEntity>
}