package smallData.web

import com.github.kittinunf.fuel.httpGet
import kotlinx.html.div
import kotlinx.html.header
import kotlinx.html.img
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import smallData.spring.flush
import smallData.view.materialPage
import smallData.view.mdl_color
import java.io.IOException
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by P on 17.09.2016.
 */
@Component
@RequestMapping("/input")
open class WebInput() {

    @GetMapping("alarm")
    fun soundAlarm(result: PrintWriter, message: String) {
        result.print("ringing alarm....")
        CallHelper.callPhoneAlarm(message)
    }

    @GetMapping
    fun showRoom(result: PrintWriter) {
        val title = "Apt. 1090"
        result.materialPage(title) {
            div("mdl_layout") {
                classes += "mdl-js-layout"
                classes += "mdl-layout--fixed-header"

                header("mdl-layout__header  mdl-layout__header--waterfall") {
                    mdl_color("light-green", "grey-800")
                    classes += "mdl-layout__header--transparent  app--layout-background"
                }
                flush()

                img("room", "http://www.rukle.com/5/g/a/architecture-floor-plans-furniture-in-room-ideas-for-small-spaces-layout-planner-plans-draw-house-home-floor-plan-how-to-event-management-system-building-design-architecture-planning-drawing-blueprint-my-space-gre-furnit_1186x837.jpg")
            }
        }

    }

}

object CallHelper{

    private val alarmURL = "https://api.tropo.com/1.0/sessions?action=create"
    private val officephone3131 = "+41444453130"
    private val tokenFile: URL = javaClass.classLoader.getResource("token")
    private val token: String = String(Files.readAllBytes(Paths.get(tokenFile.toURI())))

    fun <T> sendGET(url: URL, clazz: Class<T>): T {
        throw AssertionError("not implemented");
    }

    fun sendGet(url: URL) {
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connect()
        val res = conn.inputStream
        res.close()
    }

    fun callPhoneAlarm(message: String, phonenumber: String = officephone3131) {
        println(message)
        try {
            val url = "$alarmURL&token=$token&phonenumber=$phonenumber&msg=$message"
            url.httpGet().responseString { request, response, result ->
                //do something with response
                println(result)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}