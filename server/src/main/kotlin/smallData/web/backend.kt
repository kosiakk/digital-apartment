package smallData.web

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.io.PrintWriter

/**
 * Created by Alexander Kosenkov on 17.09.2016.
 */
@Component
@RequestMapping("/")
class Backend {


    @GetMapping("backend")
    fun view(result: PrintWriter, apartment: Int?) {



    }
}