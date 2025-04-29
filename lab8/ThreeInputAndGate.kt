package bridge

import state.ThreeInputAndGateAutomaton

class ThreeInputAndGate(private val input1 : Boolean, private val input2 : Boolean, private val input3 : Boolean, implementation : AndGateImplementation):AndGateAbstraction(implementation) {
    override fun calculate(): Boolean {
        val automaton = ThreeInputAndGateAutomaton()
        automaton.transition(input1)
        automaton.transition(input2)
        automaton.transition(input3)
        return automaton.getOutput()
    }
}