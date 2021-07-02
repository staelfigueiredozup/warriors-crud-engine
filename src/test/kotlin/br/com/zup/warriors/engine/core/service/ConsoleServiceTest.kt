package br.com.zup.warriors.engine.core.service

import br.com.zup.warriors.engine.core.ports.ConsoleRepositoryPort
import br.com.zup.warriors.engine.database.entity.ConsoleEntity
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
import br.com.zup.warriors.model.Console
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

@MicronautTest
class ConsoleServiceTest : AnnotationSpec() {

    val repository = mockk<ConsoleRepositoryPort>()
    val service = ConsoleService(repository)

    lateinit var consoleEntity: ConsoleEntity

    companion object {
        val id = UUID.randomUUID()
    }

    @BeforeEach
    fun setUp() {
        consoleEntity = ConsoleEntity(nome = "ConsoleA", marca = "MarcaA", dataLancamento = null, id = id)
    }

    @Test
    fun `deve retornar um console buscando pelo id`() {
        //cenário
        every { repository.findById(any()) } answers { consoleEntity }

        //ação
        val result = service.consultaConsole(id)

        //validação
        result.nome shouldBe "ConsoleA"
    }

    @Test
    fun `deve retornar um erro quando o console nao estiver no banco de dados`(){
        //cenário
        every { repository.findById(any()) } answers { null }

        //ação
        val result = shouldThrow<ConsoleNaoEncontradoException> {
            service.consultaConsole(id)
        }

        //validação
        result.message shouldBe "Console inexistente no banco de dados"
    }

    @Test
    fun `deve retornar uma lista com todos os consoles cadastrados`(){
        //cenário
        val console1 = ConsoleEntity(id = UUID.randomUUID(), nome = "console1", marca = "marca1", dataLancamento = LocalDate.now(), dataCadastro = LocalDate.now())
        val console2 = ConsoleEntity(id = UUID.randomUUID(), nome = "console2", marca = "marca2", dataLancamento = LocalDate.now(), dataCadastro = LocalDate.now())
        val console3 = ConsoleEntity(id = UUID.randomUUID(), nome = "console3", marca = "marca3", dataLancamento = LocalDate.now(), dataCadastro = LocalDate.now())
        val listaConsoles = listOf(console1, console2, console3)
        every { repository.findAll() } answers { listaConsoles }

        //ação
        val result = service.listaConsoles()

        //validação
        result.size shouldBe 3
        result.get(0).javaClass shouldBe Console::class.java
        result.get(0).nome shouldBe "console1"
    }

}