package smallData.db.schema

import datomic.Connection
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.inject.Inject

@Component
open class SchemaDefinition {

    @Inject
    lateinit var conn: Connection

    @PostConstruct
    fun create() {


    }
}