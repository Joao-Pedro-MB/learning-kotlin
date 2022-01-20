package parking

data class ParkingLot(val size: Int) {
    data class Car(var plate: String, var color: String) {

		init{
			plate = plate.uppercase()
			color = color.uppercase()
		}
        override fun toString() = "$plate $color"
    }

    data class Spot(val spotNumber: Int, val isFree: Boolean, val car: Car?) {
        override fun toString() = "$spotNumber $car"
    }

    private val parkingData = Array(size) { Spot(it + 1, true, null) }

    init {
        if (size != 0) println("Created a parking lot with $size spots.")
    }

    fun park(car: Car) {
        if (size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        val firstFree = parkingData.firstOrNull { it.isFree }
        if (firstFree != null) {
            parkingData[firstFree.spotNumber - 1] = Spot(firstFree.spotNumber, false, car)
            println(car.color + " car parked in spot " + firstFree.spotNumber + ".")
        } else
            println("Sorry, the parking lot is full.")
    }

    fun leave(spotNumber: Int) {
        if (size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        if (parkingData[spotNumber - 1].isFree)
            println("There is no car in spot $spotNumber.")
        else {
            println("Spot $spotNumber is free.")
            parkingData[spotNumber - 1] = Spot(spotNumber, true, null)
        }
    }

    override fun toString(): String {
        val str = parkingData.filter { !it.isFree }.joinToString("\n") { it.toString() }
        return when {
            size == 0 -> "Sorry, a parking lot has not been created."
            str.isEmpty() -> "Parking lot is empty."
            else -> str
        }
    }
    
    fun reg_by_color(color:String) {

        var str: MutableList<String> = ArrayList()
		for (i in parkingData) {
			if (i.car?.color == color.uppercase()) {   
				str.add(i.car?.plate)
			}
		}

		if (str.size == 0) {
			println("No cars with color $color were found.")
		} else {
            println(str.joinToString(", "))
        }

    }
    
    fun spot_by_reg(reg:String) {
		var sum = 0
		for (i in parkingData) {
			if (i.car?.plate == reg.uppercase()) {
				println(i.spotNumber)
				sum += 1
			}
		}

		if (sum == 0) {
			println("No cars with registration number $reg were found.")
		}
    }
    
    fun spot_by_color(color:String) {
		
        var str: MutableList<Int> = ArrayList()
		for (i in parkingData) {
			if (i.car?.color == color.uppercase()) {   
				str.add(i.spotNumber)
			}
		}

		if (str.size == 0) {
			println("No cars with color $color were found.")
		} else {
            println(str.joinToString(", "))
        }

    }
}

fun main() {
    var parkingLot:ParkingLot? = null
    val warning = "Sorry, a parking lot has not been created."
    while (true) {
        val command = readLine()!!.split(" ")
        when (command[0]) {
            "create" -> parkingLot = ParkingLot(command[1].toInt())
            "park" -> parkingLot?.park(ParkingLot.Car(command[1], command[2])) ?: println(warning)
            "leave" -> parkingLot?.leave(command[1].toInt()) ?: println(warning)
            "status" -> println(parkingLot) ?: println(warning)
            "reg_by_color" -> parkingLot?.reg_by_color(command[1]) ?: println(warning)
            "spot_by_color" -> parkingLot?.spot_by_color(command[1]) ?: println(warning)
            "spot_by_reg" -> parkingLot?.spot_by_reg(command[1]) ?: println(warning)
            "exit" -> return
        }
    }
}
