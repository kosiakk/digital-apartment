package smallData.view

import kotlinx.html.*
import smallData.spring.plus

open class BEM(val blockType: String, initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>, tag: String) :
        HTMLTag(tag, consumer, initialAttributes, null, false, false), HtmlBlockTag {

    fun elementClass(elementType: String?, vararg modifiers: String) = combinedClass(blockType, elementType, *modifiers)

    fun element(elementType: String, vararg modifiers: String, tag: String = "div", body: BEM.() -> Unit = {}): Unit {
        val elemClasses = elementClass(elementType, *modifiers)

        val div = BEM(blockType, attributesMapOf("class", elemClasses), consumer, tag)
        div.visit(body)
    }
}


fun Tag.block(type: String, vararg modifiers: String, tag: String = "div", body: BEM.() -> Unit = {}): Unit {
    val elemClasses = combinedClass(type, null, *modifiers)

    val div = BEM(type, attributesMapOf("class", type + " " + elemClasses), consumer, tag)
    div.visit(body)
}


private fun combinedClass(blockType: String, elementType: String?, vararg modifiers: String): String {
    val fullClass = if (elementType == null) blockType else blockType + "__" + elementType

    val modifiersFull = modifiers.map {
        if (it.contains("--"))
            it  // this is already a full modifier
        else
            blockType + "--" + it
    }

    val answer = if (elementType == null) {
        modifiersFull
    } else {
        (listOf(fullClass) + modifiersFull)
    }

    return answer.joinToString(" ")
}


/*
fun <T : HTMLTag> BEM<T>.element(elementType: String, vararg modifiers: String, tag: String = "div", body: T.() -> Unit = {}): Unit {
    val classesFull = modifiers.map { blockType + "--" + it }

    val elementTypeFull = blockType + "__" + elementType

    val div = BEM<T>(blockType, attributesMapOf("class", (listOf(elementTypeFull) + classesFull).joinToString(" ")), consumer, tag)
    div.visit(body)
}

*/