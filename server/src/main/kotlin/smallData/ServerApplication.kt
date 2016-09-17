package smallData

import datomic.Connection
import datomic.Database
import datomic.Peer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.web.context.WebApplicationContext
import smallData.db.schema.SchemaDefinition

@SpringBootApplication
open class ServerApplication {

    @Bean
    @Scope(WebApplicationContext.SCOPE_APPLICATION)
    open fun dbConnection(): Connection {
        val uri: String = "datomic:mem://test"
        // val uri: String = "datomic:free://localhost:4334/digital-apartment"
        Peer.createDatabase(uri)
        return Peer.connect(uri)
    }

    @Bean
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    open fun currentDatabase(conn: Connection): Database {
        // It should look for session, and request parameters
        return conn.db()
    }
}

fun main(args: Array<String>) {
    val context = SpringApplication.run(ServerApplication::class.java, *args)

    context.getBean(SchemaDefinition::class.java)
}
