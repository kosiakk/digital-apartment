package smallData.web

import kotlinx.html.BODY
import kotlinx.html.body
import kotlinx.html.h2
import kotlinx.html.p
import kotlinx.html.stream.appendHTML
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
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
        response.sendRedirect("/view?q=17592186047215")
    }


    @GetMapping("view")
    fun view(result: PrintWriter) {

        result.appendHTML().body {

            header("Welcome!")

            val what = "cool app"

            p {
                +"This is the $what to track activity within an apartment"
            }

        }

    }

    private fun BODY.header(s: String) {
        h2 {
            +s
        }
    }


}