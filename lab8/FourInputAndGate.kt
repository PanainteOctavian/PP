package bridge

import state.FourInputAndGateAutomaton

class FourInputAndGate(private val input1 : Boolean, private val input2 : Boolean, private val input3 : Boolean, private val input4 : Boolean, implementation : AndGateImplementation):AndGateAbstraction(implementation) {
    override fun calculate(): Boolean {
        val automaton = FourInputAndGateAutomaton()
        automaton.transition(input1)
        automaton.transition(input2)
        automaton.transition(input3)
        automaton.transition(input4)
        return automaton.getOutput()
    }
}