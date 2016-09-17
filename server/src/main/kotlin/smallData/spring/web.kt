package smallData.spring

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import java.io.Flushable
import java.io.PrintWriter

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
open class HtmlOutput(val result: PrintWriter) :
        TagConsumer<PrintWriter> by result.appendHTML(),
        Flushable {

    override fun flush() = result.flush()
}

fun Tag.flush() {
    // oh, man, this not perfect, but it works
    val out = consumer.finalize()
    if (out is Flushable) {
        out.flush()
    }
    // For the fun!
    // Thread.sleep(1000)
}

fun FlowContent.cssInlineStyle(raw: String) {
    if (raw.contains("</")) throw SecurityException("CSS string validation error");

    style("text/css") {
        unsafe { +raw.trimIndent() }
    }
}