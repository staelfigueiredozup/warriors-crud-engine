package br.com.zup.warriors.engine.database.repository

import br.com.zup.warriors.engine.database.entity.ConsoleEntity
import br.com.zup.warriors.model.Console
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import java.time.LocalDate
import java.util.*

@MicronautTest
class ConsoleRepositoryImplTest : AnnotationSpec() {

    val cqlSession = mockk<CqlSession>(relaxed = true)
    val repository =  ConsoleRepositoryImpl(cqlSession)
    val row = mockk<Row>(relaxed = true)
    lateinit var resultSet: ResultSet

    lateinit var console: Console
    lateinit var consoleEntity: ConsoleEntity
    lateinit var listConsole: List<ConsoleEntity>

    companion object {
        val idGerado = UUID.randomUUID()
    }

    @BeforeEach
    fun setUp(){
        consoleEntity = ConsoleEntity(nome = "consoleA", marca = "marcaA", dataCadastro = LocalDate.now(), id = idGerado, dataLancamento = LocalDate.now())
        console = Console(nome = "consoleA", marca = "marcaA", dataCadastro = LocalDate.now(), id = idGerado, dataLancamento = LocalDate.now())
        listConsole = listOf(consoleEntity, consoleEntity, consoleEntity)
    }

    @Test
    fun `deve selecionar um console pelo id e depois transformar em consoleEntity`(){
        //cenário
        every { cqlSession.execute(
            SimpleStatement
                .builder("SELECT * FROM console.console WHERE id = ?")
                .addPositionalValue(idGerado)
                .build()
        ).one() } answers { row }

        every { row.getString("nome") } answers { console.nome }
        every { row.getString("marca") } answers { console.marca }
        every { row.getLocalDate("data_lancamento") } answers { console.dataLancamento }
        every { row.getLocalDate("data_cadastro") } answers { console.dataCadastro }
        every { row.getUuid("id") } answers { idGerado }

        //ação
        val result = repository.findById(idGerado)

        //validação
        result shouldBe consoleEntity

    }

    @Test
    fun `deve selecionar a lista de consoles e depois transformar em lista de consoleEntity`(){
        //cenário

        val resultadoBusca = cqlSession.execute(
            SimpleStatement
                .builder("SELECT * FROM console.console")
                .build()
        ).map { row ->
            ConsoleEntity(id = row.getUuid("id"),
                nome = row?.getString("nome")!!,
                marca = row?.getString("marca")!!,
                dataLancamento = row?.getLocalDate("data_lancamento"),
                dataCadastro = row?.getLocalDate("data_cadastro")!!,
            )
        }.toList()

        //ação
        val result = repository.findAll()

        //validação
        result shouldBe resultadoBusca

        }


}