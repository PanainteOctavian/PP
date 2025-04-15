// abstractizarea (partea de sus a podului)
abstract class LogicGate(protected val implementation: GateImplementation) {
    abstract fun getOutput(): Boolean
}

// implementarea (partea de jos a podului)
interface GateImplementation {
    fun calculateOutput(inputs: List<Boolean>): Boolean
}

// implementare concreta
class AndGateImplementation : GateImplementation {
    override fun calculateOutput(inputs: List<Boolean>): Boolean {
        return inputs.all { it == true } // true doar daca toate intrarile sunt true
    }
}

// clasa care foloseste bridge (conecteaza abstractizarea cu implementarea)
class AndGate(implementation: GateImplementation) : LogicGate(implementation) {
    private val inputs = mutableListOf<Boolean>()

    fun addInput(input: Boolean) {
        inputs.add(input)
    }

    override fun getOutput(): Boolean {
        return implementation.calculateOutput(inputs) // deleaga calculul la implementare
    }
}

class AndGateBuilder private constructor(private val implementation: GateImplementation) {
    private val inputs = mutableListOf<Boolean>()

    companion object {
        // punct de start pentru builder
        fun create(implementation: GateImplementation): AndGateBuilder {
            return AndGateBuilder(implementation)
        }
    }

    // adauga intrari una cate una (partea de "pas cu pas")
    fun withInput(input: Boolean): AndGateBuilder {
        inputs.add(input)
        return this // returneaza builderul pentru chaining
    }

    // construieste obiectul final (partea de "finalizeaza")
    fun build(): AndGate {
        val gate = AndGate(implementation)
        inputs.forEach { gate.addInput(it) }
        return gate
    }
}

fun main() {
    // se creeaza builder-ul
    val andImplementation = AndGateImplementation()

    // poarta cu 2 intrari
    val gate2Inputs = AndGateBuilder.create(andImplementation)
        .withInput(true)
        .withInput(true)
        .build()

    // poarta cu 3 intrari
    val gate3Inputs = AndGateBuilder.create(andImplementation)
        .withInput(true)
        .withInput(true)
        .withInput(false)
        .build()

    // poarta cu 4 intrari
    val gate4Inputs = AndGateBuilder.create(andImplementation)
        .withInput(true)
        .withInput(true)
        .withInput(true)
        .withInput(true)
        .build()


    // builder pentru poarta cu 8 intrări:
    val gate8Inputs = AndGateBuilder.create(andImplementation)
        .withInput(true).withInput(true).withInput(true).withInput(true)
        .withInput(true).withInput(true).withInput(true).withInput(false)
        .build()

    println("AND 2 inputs: ${gate2Inputs.getOutput()}")
    println("AND 3 inputs: ${gate3Inputs.getOutput()}")
    println("AND 4 inputs: ${gate4Inputs.getOutput()}")
    println("AND 8 inputs: ${gate8Inputs.getOutput()}")
}
