package bridge

class AndGate : AndGateImplementation {
    override fun calculate(vararg inputs: Boolean): Boolean {
        for(input in inputs) {
            if(!input) {
                return false
            }
        }
        return true
    }
}