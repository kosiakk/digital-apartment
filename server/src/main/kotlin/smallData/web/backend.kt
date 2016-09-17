package smallData.web

import kotlinx.html.DIV
import kotlinx.html.div
import kotlinx.html.img
import kotlinx.html.span
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import smallData.view.materialPage
import java.io.PrintWriter
import javax.inject.Inject

/**
 * Created by Alexander Kosenkov on 17.09.2016.
 */
@Component
@RequestMapping("/")
class Backend {

    @Inject
    lateinit var sensor: SensorManager

    @GetMapping("backend")
    fun view(result: PrintWriter, apartment: Int?) {

        result.materialPage("Floor plan") {

            div(classes = "online-update") {

                img(src = "/img/floor_plan.png") {
                    style = "width: 600px"
                }

                sensor(sensor.balconyDoor, 70, 130)
                sensor(sensor.livingroomMovement, 160, 120)

                sensor(sensor.frontDoor, 450, 240)


//                val bedroomMovement = SensorManager.Sensor("Bedroom", SensorManager.SensorType.MOVEMENT)
//                val  = SensorManager.Sensor("Living room", SensorManager.SensorType.MOVEMENT)
//                val livingroomWindow = SensorManager.Sensor("Living room", SensorManager.SensorType.WINDOW)
//                val kitchenMovement = SensorManager.Sensor("Kitchen", SensorManager.SensorType.MOVEMENT)
//                val kitchenRefrigator = SensorManager.Sensor("Kitchen", SensorManager.SensorType.WINDOW)
//                val  = SensorManager.Sensor("Front door", SensorManager.SensorType.DOOR)
//                val balconyDoor = SensorManager.Sensor("Balcony door", SensorManager.SensorType.DOOR)
//                val balconyWindow = SensorManager.Sensor("Balcony window", SensorManager.SensorType.WINDOW)


            }

        }

    }

    private fun DIV.sensor(s: SensorManager.Sensor, y: Int, x: Int) {
        span {
            style = "position:absolute; top: ${x}px; left: ${y}px;"

            onClick = "toggle('${s.location}');"
            sensorIcon(s)
        }
    }
}