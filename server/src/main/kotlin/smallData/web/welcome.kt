package smallData.web

import kotlinx.html.*
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import smallData.spring.flush
import smallData.view.materialPage
import smallData.view.mdl_color
import smallData.view.mdl_icon
import java.io.PrintWriter
import java.util.*
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
                    mdl_color("light-green", "grey-800")

                    classes += "mdl-layout__header--transparent  app--layout-background"

                    // Top row, always visible
                    div("mdl-layout__header-row") {
                        span("mdl-layout-title") { +title }

                        spacer()

                        span("app__visible-if-compact") {
                            mdl_icon("check circle")
                            +"all is OK"
                        }

                        nav(classes = "mdl-navigation") {
                            a(classes = "mdl-navigation__link", href = "#") { +"Link" }
                        }
                    }

                    // Bottom row, not visible on scroll
                    div("mdl-layout__header-row") {
                        style = "height: 50vh"

                        spacer()

                        mdl_icon("check circle") {
                            classes += "md-90"
                        }

                        +"all is OK"
                    }
                }
                div("mdl-layout__drawer") {
                    span("mdl-layout-title") { +title }
                    nav(classes = "mdl-navigation") {
                        a(classes = "mdl-navigation__link", href = "#") { +"Link" }
                    }
                }

                flush()

                div("mdl-layout__content") {

                    div("page-content") {

                        h4 { +"Details:" }


                        +Date().toString()


                        ul("mdl-list") {
                            for (i in 0..20) {
                                li("mdl-list__item  mdl-list__item--two-line") {
                                    span("mdl-list__item-primary-content") {
                                        span {
                                            +"Window"
                                        }
                                        span("mdl-list__item-sub-title") {
                                            +"Bedroom"
                                        }
                                    }
                                    span("mdl-list__item-secondary-content") {

                                        statusIcon()

                                    }
                                }
                            }

                        }
                    }

                }
            }
        }


    }

    private fun SPAN.statusIcon() {
        span("mdl-list__item-secondary-action") {
            mdl_icon("lock") {
                mdl_color("white", "green")
            }
        }
        span("mdl-list__item-secondary-info") { +"closed" }
    }

    private fun DIV.spacer() {
        div("mdl-layout-spacer")
    }

}
