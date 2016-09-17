package smallData.web

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.WebApplicationContext
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

    enum class SensorType(val description: String, val stateOnIcon: String, val stateOffIcon: String, stateOnColor: String, stateOffColor: String) {
        BRIGHTNESS("brightness", "lightbulb", "lightbulb", "yellow", "black"),
        MOVEMENT("movement", "walk", "human-male", "blue", "black"),
        FIREDETECTOR("fire detector", "fire", "fire", "red", "black"),
        WATERTEMP("water temperature", "oil-temperature", "oil-temperature", "red", "black"),
        DOOR("door open", "lock-open", "lock", "yellow", "black"),
        WINDOW("window open", "lock-open", "lock", "yellow", "black")
    }

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
        val bedroomBrightness = Sensor("Bedroom", SensorType.BRIGHTNESS)
        val bedroomMovement = Sensor("Bedroom", SensorType.MOVEMENT)
        val livingroomBrightness = Sensor("Living room", SensorType.BRIGHTNESS)
        val livingroomMovement = Sensor("Living room", SensorType.MOVEMENT)
        val livingroomWindow = Sensor("Living room", SensorType.WINDOW)
        val kitchenWaterTemp = Sensor("Kitchen", SensorType.WATERTEMP)
        val kitchenMovement = Sensor("Kitchen", SensorType.MOVEMENT)
        val kitchenRefrigator = Sensor("Kitchen", SensorType.WINDOW)
        val frontDoor = Sensor("Front door", SensorType.DOOR)
        val balconyDoor = Sensor("Balcony door", SensorType.DOOR)
        val balconyWindow = Sensor("Balcony window", SensorType.WINDOW)


        val rng = Random(1337)

        var nValues = 20

        for (i in 0..nValues) {
            bedroomBrightness.dataHistory.add(SensorData(false, LocalDateTime.now().minusDays(rng.nextInt(255).toLong()).minusHours(rng.nextInt(255).toLong())))
            bedroomMovement.dataHistory.add(SensorData(false, LocalDateTime.now().minusDays(rng.nextInt(255).toLong()).minusHours(rng.nextInt(255).toLong())))
            livingroomBrightness.dataHistory.add(SensorData(false, LocalDateTime.now().minusDays(rng.nextInt(255).toLong()).minusHours(rng.nextInt(255).toLong())))
            livingroomMovement.dataHistory.add(SensorData(false, LocalDateTime.now().minusDays(rng.nextInt(255).toLong()).minusHours(rng.nextInt(255).toLong())))
        }

        sensordataMap.put(SensorType.MOVEMENT, mutableListOf(livingroomMovement, kitchenMovement, bedroomMovement))
        sensordataMap.put(SensorType.BRIGHTNESS, mutableListOf(bedroomBrightness, livingroomBrightness))
        sensordataMap.put(SensorType.DOOR, mutableListOf(frontDoor, balconyDoor, kitchenRefrigator))
        sensordataMap.put(SensorType.WINDOW, mutableListOf(livingroomWindow, balconyWindow))

        sensordataMap.values.forEach { sensordata.addAll(it) }

        nValues = 5
        for (i in 0..nValues) {
            for (s in sensordata) {
                s.dataHistory.add(SensorData(false, LocalDateTime.now().minusDays(rng.nextInt(255).toLong()).minusHours(rng.nextInt(255).toLong())))
            }
        }
    }

    fun computeWarningLevel() {
        //magic heuristics


        val doorclosed = sensordataMap.get(SensorType.DOOR)?.filter { it.isOn() }?.any() ?: false
        val windowOpen = sensordataMap.get(SensorType.WINDOW)?.filter { it.isOn() }?.any() ?: false
        val hasNoMovement = sensordataMap.get(SensorType.DOOR)?.filterNot { it.isOn() }?.any() ?: false

        if (windowOpen && hasNoMovement) {
            warningLevel = WaringLevel.YELLOW
            if (doorclosed) {
                warningLevel = WaringLevel.RED
            }
        }


    }


    @PostMapping("register")
    fun registerSensor(sensortype: String, location: String, id: Int) {
        when (sensortype) {
            "brigthness" -> sensordata.add(Sensor(location, SensorType.BRIGHTNESS, mutableListOf()))
        }
    }

    @PostMapping("data")
    fun putSensorData(id: Int, value: Boolean) {

    }

    @GetMapping("closedoor")
    fun closeDoor() {
        sensordataMap.get(SensorType.DOOR)?.filter { it.location == "Front door" }?.forEach { d -> d.dataHistory.add(SensorData(false, LocalDateTime.now())) }
    }

}





