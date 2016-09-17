package smallData.web

import com.github.kittinunf.fuel.httpGet
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
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

    val alarmURL = "https://api.tropo.com/1.0/sessions?action=create"
    val officephone3131 = "+41444453131"

    @GetMapping("alarm")
    fun soundAlarm(result: PrintWriter, message : String) {
        result.print("ringing alarm....")
        callPhoneAlarm(message)
    }

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

    fun callPhoneAlarm(message: String, phonenumber : String = officephone3131){
        try{
//            val url = URL(alarmURL+"&phonenumber=$phonenumber&msg=$message")
            val tokenFile : URL = javaClass.classLoader.getResource("token")
            val token : String= String(Files.readAllBytes(Paths.get(tokenFile.toURI())))

            val url = "$alarmURL&token=$token&phonenumber=$phonenumber&msg=$message"

            url.httpGet().responseString { request, response, result ->
                //do something with response
                when (result) {
                    else -> println("no result")
                }
            }
        } catch (e : IOException){
            e.printStackTrace()
        }

    }

}