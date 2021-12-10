package days

import java.util.Stack


class Day18 : Day(18) {

    override fun partOne(): Any {
        val result: List<Element> = inputList.map { calculate(Stack(), it.toStack(), Stack()).pop() }
        val res = result.map { it as Operand }.map { it.value }.sum()
        return res
    }

    private fun calculate(log: Stack<Operator>, input: Stack<Char>, output: Stack<Operand>): Stack<Operand> {
        if (input.empty()) {
            op(log, output)
            return output
        }
        val current = input.pop()

        return when (current) {
            '(' -> calculate(log.fluentPush(Operator(current)), input, output)
            '+' -> {
                op(log, output, current.toOperator())
                calculate(log, input, output)
            }
            '*' -> {
                op(log, output, current.toOperator())
                calculate(log, input, output)
            }
            ')' -> {
                op(log, output, current.toOperator())
                calculate(log, input, output)
            }
            else -> calculate(log, input, output.fluentPush(current.toOperand()))
        }

    }

    private fun op(log: Stack<Operator>, output: Stack<Operand>, current: Operator) {
     //   println("Op3 $log $output $current")
        if (log.empty()) {
            assert(current.value != ')')
            log.push(current)
            return
        }
        val op = log.pop()
        if (op.value == '*') {
            if (current.value == '+') {
                log.push(op)
                log.push(current)
                return

            }
            output.push(Operand(output.pop().value * output.pop().value))
            return op(log, output, current)
        }
        if (op.value == '+') {
            output.push(Operand(output.pop().value + output.pop().value))
            return op(log, output, current)
        }
        if (op.value == '(') {
           // println("XDDDDD $current")
            if (current.value == ')') {
                return
            }
            log.push(op)
            log.push(current)
            return
        }
    }

    private fun op(log: Stack<Operator>, output: Stack<Operand>) {
      //  println("Op2 $log $output")
        if (log.empty()) {
            return
        }
        val op = log.pop()
        if (op.value == '+') {
            output.push(Operand(output.pop().value + output.pop().value))
            return op(log, output)
        }
        if (op.value == '*') {
            output.push(Operand(output.pop().value * output.pop().value))
            return op(log, output)
        }
    }


//    private fun deleteBrackets(log: Stack<Element>): Stack<Element> {
//        val num = log.pop() as Operand
//        val bracket = log.pop()
//        assert(bracket is Operator && bracket.value == '(') { bracket }
//        return calculate(log, num.value)
//    }

    override fun partTwo(): Any {
        return 0
    }

}

private sealed class Element
private data class Operand(val value: Long) : Element()
private data class Operator(val value: Char) : Element()

private fun Stack<Char>.fluentPush(element: Char): Stack<Char> {
    this.push(element)
    return this
}

private fun Stack<Operator>.fluentPush(element: Operator): Stack<Operator> {
    this.push(element)
    return this
}


private fun Stack<Operand>.fluentPush(element: Operand): Stack<Operand> {
    this.push(element)
    return this
}

private fun Char.toOperator() = Operator(this)
private fun Char.toOperand() = Operand(this.toString().toLong())


private fun String.toStack() =
        this.reversed().filter { it != ' ' }.fold(Stack<Char>()) { s, i -> s.fluentPush(i) }
