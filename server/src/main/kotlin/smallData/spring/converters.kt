package smallData.spring

import datomic.Attribute
import datomic.Database
import datomic.Entity
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import smallData.db.str

// Datomic

@Component
open class AttributeConverter(val beanFactory: ListableBeanFactory) : Converter<String, Attribute> {
    override fun convert(source: String?): Attribute? {
        if (source == null || source.isEmpty()) return null

        val db = beanFactory.getBean(Database::class.java)

        if (source.startsWith(':'))
            return db.attribute(source)

        val id = source.toLong()
        return db.attribute(id)
    }
}

@Component
open class EntityConverter(val beanFactory: ListableBeanFactory) : Converter<String, Entity> {
    override fun convert(source: String?): Entity? {
        if (source == null || source.isEmpty()) return null

        val db = beanFactory.getBean(Database::class.java)

        if (source.startsWith(':'))
            return db.entity(source)

        val id = source.toLong()
        return db.entity(id)
    }
}

@Component
open class AttributeConverterString() : Converter<Attribute, String> {
    override fun convert(attr: Attribute?) = attr?.str
}

@Component
open class EntityConverterString() : Converter<Entity, String> {
    override fun convert(e: Entity?) = e?.str
}
