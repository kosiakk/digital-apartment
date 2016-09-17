package smallData.view

import kotlinx.html.*
import kotlinx.html.consumers.delayed
import smallData.spring.HtmlOutput
import java.io.PrintWriter


fun CommonAttributeGroupFacade.mdl_color(color: String? = null, text: String? = null) {
    if (color != null)
        classes += "mdl-color--$color"

    if (text != null)
        classes += "mdl-color-text--$text"
}


fun FlowContent.linea_icon(icon: String, size: Int = 64, bodyContent: (IMG.() -> Unit)? = null) {

    img(src = "/linea/$icon.svg", alt = icon) {
        height = "${size}px"
        width = height

        if (bodyContent != null) bodyContent()

    }
}

fun Tag.mdl_icon(icon: String, text: String? = null, bodyContent: (I.() -> Unit)? = null) {
    if (this is FlowContent) {

        i("material-icons") {
            if (bodyContent != null) {
                bodyContent()
            }

            +icon
        }
        if (text != null) {
            span(classes = "visuallyhidden") {
                +text
            }
        }

    } else if (this is PhrasingContent) {

        // UGLY CODE DUPLICATE
        // Bad, bad koltinx.html

        i("material-icons") {
            if (bodyContent != null) {
                bodyContent()
            }

            +icon
        }
        if (text != null) {
            span(classes = "visuallyhidden") {
                +text
            }
        }
    }
}


fun PrintWriter.materialPage(title: String, pageStyle: String? = null, bodyContent: BODY.() -> Unit) {
    println("<!doctype html>")
    HtmlOutput(this).delayed().html {
        head {
            meta(name = "charset", content = "utf-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0, minimum-scale=1.0")
            link(rel = "stylesheet", href = "/app.css")
            link(rel = "stylesheet", href = "/material.css")
            link(rel = "stylesheet", href = "https://fonts.googleapis.com/icon?family=Material+Icons")

            script(type = ScriptType.textJavaScript, src = "/material.js") { async = true }
            script(type = ScriptType.textJavaScript, src = "/app.js") { async = true }
            script(type = ScriptType.textJavaScript, src = "/morphdom-umd.js") { async = true }
            if (pageStyle != null)
                style(type = "text/css", content = pageStyle)

            flush()  // so that browser might start parsing

            title { +title }

        }

        body {
            bodyContent()
        }
    }
}

private fun HEAD.metaDescription(description: String, title: String, canonicalUrl: String?, imageUrl: String?) {
    // facebook open graph tags
    meta(name = "og:type", content = "website")
    if (canonicalUrl != null)
        meta(name = "og:url", content = canonicalUrl)

    meta(name = "og:title", content = title)
    meta(name = "og:description", content = description)
    if (imageUrl != null)
        meta(name = "og:image", content = imageUrl)

    // twitter card tags additive with the og: tags
    if (imageUrl == null)
        meta(name = "twitter:card", content = "summary")
    else
        meta(name = "twitter:card", content = "summary_large_image")
    // meta(name = "twitter:domain", content = "ruraljuror.com")
    meta(name = "twitter:title", content = title)
    meta(name = "twitter:description", content = description)
    if (imageUrl != null)
        meta(name = "twitter:image", content = imageUrl)

    // deprecated, but might be useful
    meta(name = "twitter:label1", content = "Opens in Theaters")
    meta(name = "twitter:data1", content = "December 1, 2015")

    meta(name = "twitter:label2", content = "Or on demand")
    meta(name = "twitter:data2", content = "at Hulu.com")
}
