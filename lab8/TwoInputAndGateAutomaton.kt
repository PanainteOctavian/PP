package state

class TwoInputAndGateAutomaton {
    private var state = 0

    fun transition(input : Boolean) {
        state = if(input) 1 else 0
    }

    fun getOutput() : Boolean {
        return state == 1
    }
}