package br.com.zup.warriors.engine.entrypoint.controller

import br.com.zup.warriors.engine.core.ports.ConsoleServicePort
import br.com.zup.warriors.engine.database.repository.ConsoleEntityRepository
import br.com.zup.warriors.engine.entrypoint.dto.ConsoleResponse
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
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

    val repository = mockk<ConsoleEntityRepository>()
    val service = mockk<ConsoleServicePort>()
    val controller = ConsolesController(service)

    lateinit var consoleResponse: ConsoleResponse

    companion object {
        val id = UUID.randomUUID()
    }

    @BeforeEach
    fun setUp(){
        consoleResponse = ConsoleResponse(id = id, nome = "ConsoleA", marca = "MarcaA", dataCadastro = LocalDate.now().toString(), dataLancamento = null)
    }

    @Test
    fun `deve retornar um console buscando pelo id`(){
        //cenário
        every { service.consultaConsole(any()) } answers { consoleResponse }

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
        val console1 = ConsoleResponse(UUID.randomUUID(),"super nintendo", "nintendo", LocalDate.now().toString(), LocalDate.now().toString())
        val console2 = ConsoleResponse(UUID.randomUUID(),"playstation", "sony", LocalDate.now().toString(), LocalDate.now().toString())
        val console3 = ConsoleResponse(UUID.randomUUID(),"megadrive", "sega", LocalDate.now().toString(), LocalDate.now().toString())
        val listaConsoles: List<ConsoleResponse> = listOf(console1, console2, console3)
        every { service.listaConsoles() } answers { listaConsoles }

        //ação
        val result = controller.listaConsoles()

        //validação
        result.body() shouldBe listaConsoles

    }

}