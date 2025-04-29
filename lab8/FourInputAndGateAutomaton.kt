package state

class FourInputAndGateAutomaton {
    private var state = 0

    fun transition(input : Boolean) {
        state = if(input) state + 1 else 0
    }

    fun getOutput() : Boolean {
        return state == 4
    }
}
