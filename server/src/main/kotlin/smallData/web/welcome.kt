package smallData.web

import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.header
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

        result.materialPage("Apt. $apartment") {

            div("mdl-layout mdl-layout--mdl-js-layout") {
                header("mdl-layout__header mdl-layout--waterfall") {

                    div("mdl-layout__header-row") {
                        style = "height: 5 cm"
                        +"text"
                    }

                }
            }
            flush()

            h2{+"Details:"}

        }


    }


}