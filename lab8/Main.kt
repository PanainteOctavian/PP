import bridge.*
import builder.*

fun main() {
    val twoInputAndGateBuilder = AndGateBuilder(AndGate())
    val threeInputAndGateBuilder = AndGateBuilder(AndGate())
    val fourInputAndGateBuilder = AndGateBuilder(AndGate())
    val eightInputAndGateBuilder = AndGateBuilder(AndGate())

    twoInputAndGateBuilder.addInput(true)
    twoInputAndGateBuilder.addInput(false)
    val twoInputAndGate = twoInputAndGateBuilder.build()
    threeInputAndGateBuilder.addInput(true)
    threeInputAndGateBuilder.addInput(true)
    threeInputAndGateBuilder.addInput(false)
    val threeInputAndGate = threeInputAndGateBuilder.build()
    fourInputAndGateBuilder.addInput(true)
    fourInputAndGateBuilder.addInput(true)
    fourInputAndGateBuilder.addInput(true)
    fourInputAndGateBuilder.addInput(true)
    val fourInputAndGate = fourInputAndGateBuilder.build()
    eightInputAndGateBuilder.addInput(true)
    eightInputAndGateBuilder.addInput(true)
    eightInputAndGateBuilder.addInput(true)
    eightInputAndGateBuilder.addInput(true)
    eightInputAndGateBuilder.addInput(true)
    eightInputAndGateBuilder.addInput(true)
    eightInputAndGateBuilder.addInput(true)
    eightInputAndGateBuilder.addInput(false)
    val eightInputAndGate = eightInputAndGateBuilder.build()

    println("Iesirea portii AND cu doua intrari: ${twoInputAndGate.calculate()}")
    println("Iesirea portii AND cu trei intrari: ${threeInputAndGate.calculate()}")
    println("Iesirea portii AND cu patru intrari: ${fourInputAndGate.calculate()}")
    println("Iesirea portii AND cu opt intrari: ${eightInputAndGate.calculate()}")
}