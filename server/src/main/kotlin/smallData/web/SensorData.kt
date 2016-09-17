package smallData.web

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.WebApplicationContext
import java.io.Reader
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct

/**
 * Created by P on 17.09.2016.
 */
@Component
@RequestMapping("/sensor")
@Scope(WebApplicationContext.SCOPE_APPLICATION)
open class SensorManager {
    var warningLevel: WaringLevel
        get() = warningLevel
        private set(value) {
            warningLevel = value
        }

    enum class WaringLevel {
        GREEN, YELLOW, RED
    }

    enum class SensorType(val description: String, val stateOnIcon: String, val stateOffIcon: String, val stateOnColor: String, val stateOffColor: String) {
        BRIGHTNESS("brightness", "lightbulb", "lightbulb", "yellow", "green"),
        MOVEMENT("movement", "directions_walk", "accessibility", "blue", "green"),
        FIREDETECTOR("fire detector", "pets", "pets", "red", "green"),
        DOOR("door open", "lock_open", "lock", "orange", "green"),
        WINDOW("window open", "lock_open", "lock", "orange", "green")
    }

    val bedroomMovement = Sensor("Bedroom", SensorType.MOVEMENT)
    val livingroomMovement = Sensor("Living room", SensorType.MOVEMENT)
    val livingroomWindow = Sensor("Living room", SensorType.WINDOW)
    val kitchenMovement = Sensor("Kitchen", SensorType.MOVEMENT)
    val kitchenRefrigator = Sensor("Kitchen", SensorType.WINDOW)
    val frontDoor = Sensor("Front door", SensorType.DOOR)
    val balconyDoor = Sensor("Balcony door", SensorType.DOOR)
    val balconyWindow = Sensor("Balcony window", SensorType.WINDOW)


    data class Sensor(val location: String, val type: SensorType, val dataHistory: MutableList<SensorData> = mutableListOf()) {
        fun isOn() = dataHistory.isNotEmpty() && dataHistory.last().value == true
    }

    open class SensorData(val value: Boolean, val timestamp: LocalDateTime)
    class TimestampedSensorData(val value: Boolean, val timestamp: LocalDateTime)


    val sensordata = ArrayList<Sensor>()
    val sensordataMap = HashMap<SensorType, MutableList<Sensor>>()

    @PostConstruct
    fun initializeWithDummyData() {
        //dummy data helpers


        val rng = Random(1337)

        val nValues = 20

        sensordataMap.put(SensorType.MOVEMENT, mutableListOf(livingroomMovement, kitchenMovement, bedroomMovement))
        sensordataMap.put(SensorType.DOOR, mutableListOf(frontDoor, balconyDoor))
        sensordataMap.put(SensorType.WINDOW, mutableListOf(livingroomWindow, balconyWindow))

        sensordataMap.values.forEach { sensordata.addAll(it) }

        for (i in 0..nValues) {
            for (s in sensordata) {
                s.dataHistory.add(SensorData(false, LocalDateTime.now().minusDays(rng.nextInt(255).toLong()).minusHours(rng.nextInt(255).toLong())))
            }
        }
    }

    fun computeWarningLevel() {
        //magic heuristics


        val doorclosed = sensordataMap[SensorType.DOOR]?.filter { it.isOn() }?.any() ?: false
        val windowOpen = sensordataMap[SensorType.WINDOW]?.filter { it.isOn() }?.any() ?: false
        val hasNoMovement = sensordataMap[SensorType.DOOR]?.filterNot { it.isOn() }?.any() ?: false

        warningLevel = WaringLevel.GREEN
        if (windowOpen && hasNoMovement) {
            warningLevel = WaringLevel.YELLOW
            if (doorclosed) {
                warningLevel = WaringLevel.RED

                // toDo call
            }
        }


    }


    @PostMapping("register")
    fun registerSensor(sensortype: String, location: String, id: Int) {
        when (sensortype) {
            "brigthness" -> sensordata.add(Sensor(location, SensorType.BRIGHTNESS, mutableListOf()))
        }
    }

    @PostMapping("toggle")
    fun toggle(body: Reader) {

        val location = body.readText()

        val sensor = sensordata.find { it.location == location } ?: return

        val current = sensor.dataHistory.last().value

        sensor.dataHistory.add(SensorData(!current, LocalDateTime.now()))

        computeWarningLevel()
    }

    @GetMapping("closedoor")
    fun closeDoor() {
        sensordataMap[SensorType.DOOR]?.filter { it.location == "Front door" }?.forEach { d -> d.dataHistory.add(SensorData(false, LocalDateTime.now())) }
    }

}





