package smallData.web

import kotlinx.html.*
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

                sensor(sensor.livingroomWindow, 540, 140)


                if (!sensor.warningsEnabled) {
                    form(method = FormMethod.post, action = "/sensor/enableWarnings") {
                        submitInput() {
                            value = "Enable alerts"
                        }
                    }

                }

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