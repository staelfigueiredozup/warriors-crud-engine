package br.com.zup.warriors.repository

import br.com.zup.warriors.model.Console
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import java.util.*
import javax.inject.Singleton

@Singleton
class ConsoleRepositoryImpl(private val cqlSession: CqlSession) : ConsoleRepository {

    override fun findById(id: UUID): Console? {

        val row: Row? = cqlSession.execute(
            SimpleStatement
                .builder("SELECT * FROM console.console WHERE id = ?")
                .addPositionalValue(id)
                .build()
        ).one() ?: return null

        return Console(
            nome = row?.getString("nome")!!,
            marca = row?.getString("marca")!!,
            dataLancamento = row?.getLocalDate("data_lancamento"),
            dataCadastro = row?.getLocalDate("data_cadastro")!!,
            id = row?.getUuid("id")
        )
    }

    override fun findAll(): List<Console> {

        val resultadoBusca: ResultSet = cqlSession.execute(
            SimpleStatement
                .builder("SELECT * FROM console.console")
                .build()
        )

        var listaConsoles = resultadoBusca.map { row ->
            Console(id = row.getUuid("id"),
                nome = row?.getString("nome")!!,
                marca = row?.getString("marca")!!,
                dataLancamento = row?.getLocalDate("data_lancamento"),
                dataCadastro = row?.getLocalDate("data_cadastro")!!,
            )
        }.toList()
        return listaConsoles
    }

}