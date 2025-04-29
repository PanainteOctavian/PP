package bridge

import state.TwoInputAndGateAutomaton

class TwoInputAndGate(private val input1 : Boolean, private val input2 : Boolean, implementation : AndGateImplementation):AndGateAbstraction(implementation) {
    override fun calculate(): Boolean {
        val automaton = TwoInputAndGateAutomaton()
        automaton.transition(input1)
        automaton.transition(input2)
        return automaton.getOutput()
    }
}