package builder

import bridge.*

class AndGateBuilder(private val implementation: AndGateImplementation) {
    private val inputs = mutableListOf<Boolean>()
    fun addInput(input : Boolean) {
        inputs.add(input)
    }

    fun build() : AndGateAbstraction {
        return when(inputs.size) {
            2-> {
                TwoInputAndGate(inputs[0], inputs[1], implementation)
            }

            3-> {
                ThreeInputAndGate(inputs[0], inputs[1], inputs[2], implementation)
            }

            4-> {
                FourInputAndGate(inputs[0], inputs[1], inputs[2], inputs[3], implementation)
            }

            8-> {
                EightInputAndGate(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7], implementation)
            }

            else-> {
                throw IllegalArgumentException("Numar invalid de inputuri, trebuie sa fie doar : 2, 3, 4 sau 8!")
            }
        }
    }
}