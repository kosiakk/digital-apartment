package smallData.web

import kotlinx.html.SPAN
import kotlinx.html.span
import smallData.view.mdl_color
import smallData.view.mdl_icon

/**
 * Created by Alexander Kosenkov on 17.09.2016.
 */

fun SPAN.sensorIcon(sensor: SensorManager.Sensor) {
    val lastOrNull = sensor.dataHistory.lastOrNull()

    val onOff = lastOrNull?.value ?: false

    val icon = if (onOff) {
        sensor.type.stateOnIcon
    } else {
        sensor.type.stateOffIcon
    }

    val color = if (onOff) {
        sensor.type.stateOnColor
    } else {
        sensor.type.stateOffColor
    }

    span("mdl-list__item-secondary-action") {
        mdl_icon(icon) {
            mdl_color("white", color)
            classes += "md-48"
        }
    }
}