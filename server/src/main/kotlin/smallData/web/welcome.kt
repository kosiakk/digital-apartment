package smallData.web

import kotlinx.html.*
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import smallData.spring.flush
import smallData.view.materialPage
import java.io.PrintWriter
import javax.servlet.http.HttpServletResponse

/**
 * Welcome page for the web app
 */

@Component
@RequestMapping("/")
class Welcome {

    @GetMapping
    fun index(response: HttpServletResponse) {
        response.sendRedirect("/welcome?apartment=1920")
    }


    @GetMapping("welcome")
    fun view(result: PrintWriter, apartment: Int?) {

        val title = "Apt. $apartment"

        result.materialPage(title) {

            div("mdl-layout") {
                classes += "mdl-js-layout"
                classes += "mdl-layout--fixed-header"

                header("mdl-layout__header  mdl-layout__header--waterfall") {

                    // Top row, always visible
                    div("mdl-layout__header-row") {
                        span("mdl-layout-title") { +title }

                        spacer()

                        nav(classes = "mdl-navigation") {
                            a(classes = "mdl-navigation__link", href = "#") { +"Link" }
                        }
                    }

                    // Bottom row, not visible on scroll
                    div("mdl-layout__header-row") {
                        spacer()
                        +"second row"
                    }
                }

                flush()

                div("mdl-layout__content") {

                    div("page-content") {

                        h2 { +"Details:" }

                        ul {
                            for (i in 0..50) {
                                li { +"item $i" }
                            }

                        }
                    }

                }
            }
        }


    }

    private fun DIV.spacer() {
        div("mdl-layout-spacer")
    }

}
