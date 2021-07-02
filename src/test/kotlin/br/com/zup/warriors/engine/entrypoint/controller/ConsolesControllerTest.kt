package br.com.zup.warriors.engine.entrypoint.controller

import br.com.zup.warriors.engine.core.mapper.ConsoleConverter
import br.com.zup.warriors.engine.core.ports.ConsoleServicePort
import br.com.zup.warriors.engine.core.ports.ConsoleRepositoryPort
import br.com.zup.warriors.engine.entrypoint.dto.ConsoleResponse
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
import br.com.zup.warriors.model.Console
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

@MicronautTest
class ConsolesControllerTest : AnnotationSpec() {

    val repository = mockk<ConsoleRepositoryPort>()
    val service = mockk<ConsoleServicePort>()
    val controller = ConsolesController(service)
    val converter = mockk<ConsoleConverter.Companion>()

    lateinit var consoleResponse: ConsoleResponse
    lateinit var console: Console

    companion object {
        val id = UUID.randomUUID()
    }

    @BeforeEach
    fun setUp(){
        consoleResponse = ConsoleResponse(id = id, nome = "ConsoleA", marca = "MarcaA", dataCadastro = LocalDate.now().toString(), dataLancamento = LocalDate.now().toString())
        console = Console(id = id, nome = "ConsoleA", marca = "MarcaA", dataCadastro = LocalDate.now(), dataLancamento = LocalDate.now())
    }

    @Test
    fun `deve retornar um console buscando pelo id`(){
        //cenário
        every { service.consultaConsole(id) } answers { console }
        every { converter.consoleToConsoleResponse(console) } answers { consoleResponse }

        //ação
        val result = controller.consultaConsole(id.toString())

        //validação
        result.status.code shouldBe HttpStatus.OK.code
        result.body() shouldBe consoleResponse
    }

    @Test
    fun `deve retornar um erro quando o console não existir no banco de dados`(){
        //cenário
        every { repository.findById(id) } answers { null }
        every { service.consultaConsole(id) } throws ConsoleNaoEncontradoException("Console inexistente no banco de dados")

        //ação
        val result = controller.consultaConsole(id.toString())

        //atualização
        result.status.code shouldBe HttpStatus.NOT_FOUND.code
    }

    @Test
    fun `deve retornar erro quando acontecer algum problema inesperado` () {
        //cenário
        every { repository.findById(id) } answers { null }
        every { service.consultaConsole(id) } throws RuntimeException()

        //ação
        val result = controller.consultaConsole(id.toString())

        //validação
        result.status.code shouldBe HttpStatus.INTERNAL_SERVER_ERROR.code
    }

    @Test
    fun `deve retornar uma lista com todos os consoles cadastrados`(){
        //cenário
        val console1 = Console(nome = "super nintendo", marca = "nintendo", dataLancamento = LocalDate.now(), id = UUID.randomUUID(), dataCadastro = LocalDate.now())
        val console2 = Console(nome = "playstation", marca = "sony", dataLancamento = LocalDate.now(), id = UUID.randomUUID(), dataCadastro = LocalDate.now())
        val console3 = Console(nome = "megadrive", marca = "sega", dataLancamento = LocalDate.now(), id = UUID.randomUUID(), dataCadastro =  LocalDate.now())

        val console4 = ConsoleResponse(nome = "super nintendo", marca = "nintendo", dataLancamento = LocalDate.now().toString(), id = console1.id, dataCadastro = LocalDate.now().toString())
        val console5 = ConsoleResponse(nome = "playstation", marca = "sony", dataLancamento = LocalDate.now().toString(), id = console2.id, dataCadastro = LocalDate.now().toString())
        val console6 = ConsoleResponse(nome = "megadrive", marca = "sega", dataLancamento = LocalDate.now().toString(), id = console3.id, dataCadastro =  LocalDate.now().toString())

        val listaConsoles: List<Console> = listOf(console1, console2, console3)
        val listaConsoleResponse: List<ConsoleResponse> = listOf(console4, console5, console6)
        every { service.listaConsoles() } answers { listaConsoles }
        every { converter.listaConsolesToListaConsoleResponse(listaConsoles) } answers { listaConsoleResponse }

        //ação
        val result = controller.listaConsoles()

        //validação
        result.body() shouldBe listaConsoleResponse

    }

}