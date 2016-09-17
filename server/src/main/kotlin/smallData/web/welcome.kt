package smallData.web

import kotlinx.html.*
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import smallData.spring.flush
import smallData.view.materialPage
import smallData.view.mdl_color
import java.io.PrintWriter
import javax.inject.Inject
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


    @Inject
    lateinit var sensor: SensorManager

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

                    classes += "online-update-style"
                    style = "background: url(/img/${sensor.warningLevel.name.toLowerCase()}.jpg) center / cover;"

                    // Top row, always visible
                    div("mdl-layout__header-row") {
                        span("mdl-layout-title") { +title }

                        spacer()

                        /*span("app__visible-if-compact") {
                            mdl_icon("check circle")
                            +"all is OK"
                        }*/

                        nav(classes = "mdl-navigation") {
                            a(classes = "mdl-navigation__link", href = "#") { +"Logout" }
                        }
                    }

                    // Bottom row, not visible on scroll
                    div("mdl-layout__header-row") {
                        style = "height: 30vh"

                        spacer()

                       /* mdl_icon("check circle") {
                            classes += "md-90"
                        }

                        +"all is OK"*/
                    }
                }
                div("mdl-layout__drawer") {
                    span("mdl-layout-title") { +title }
                    nav(classes = "mdl-navigation") {
                        a(classes = "mdl-navigation__link", href = "#") { +"Statistical Data" }
                    }
                }

                flush()

                div("mdl-layout__content") {

                    div("page-content") {


                        ul("mdl-list  online-update") {

                            sensor.sensordata.forEach { sensor ->


                                li("mdl-list__item  mdl-list__item--two-line") {
                                    span("mdl-list__item-primary-content") {
                                        span {
                                            +sensor.location
                                        }
                                        span("mdl-list__item-sub-title") {
                                            +sensor.type.description
                                        }
                                    }
                                    span("mdl-list__item-secondary-content") {

                                        sensorIcon(sensor)

/*

                                        span("mdl-list__item-secondary-info") {
                                            +"closed"
                                        }
*/

                                    }
                                }
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
