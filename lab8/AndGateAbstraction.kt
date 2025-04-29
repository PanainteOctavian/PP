package bridge

abstract class AndGateAbstraction(val implementation: AndGateImplementation) {
    abstract fun calculate() : Boolean
}