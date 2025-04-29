package bridge

import state.EightInputAndGateAutomaton

class EightInputAndGate(private val input1 : Boolean, private val input2 : Boolean, private val input3 : Boolean, private val input4 : Boolean, private val input5 : Boolean, private val input6 : Boolean, private val input7 : Boolean, private val input8 : Boolean, implementation : AndGateImplementation):AndGateAbstraction(implementation) {
    override fun calculate(): Boolean {
       val automaton = EightInputAndGateAutomaton()
       automaton.transition(input1)
       automaton.transition(input2)
       automaton.transition(input3)
       automaton.transition(input4)
       automaton.transition(input5)
       automaton.transition(input6)
       automaton.transition(input7)
       automaton.transition(input8)
       return automaton.getOutput()
    }
}