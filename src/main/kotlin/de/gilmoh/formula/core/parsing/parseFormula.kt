package de.gilmoh.formula.core.parsing

import de.gilmoh.formula.core.knowledge.StandardInfixFunctions
import de.gilmoh.formula.core.models.NumberValue
import de.gilmoh.formula.core.models.Priority
import de.gilmoh.formula.core.models.Variable
import de.gilmoh.formula.core.models.functions.InfixFunction
import de.gilmoh.formula.core.models.functions.PrefixFunction
import de.gilmoh.formula.core.models.interfaces.Formula

fun parseFormula(input: String): Formula {
    val processedInput = preprocessInput(input)

    when(getTopLevelFunctionType(input)) {
        FormulaType.PRIORITY -> {
            return Priority(parseFormula(processedInput.drop(1).dropLast(1)))
        }
        FormulaType.INFIX -> {
            val withoutParenthesisContent = removeContentOfParenthesis(processedInput)
            val delimited = withoutParenthesisContent.split(" ")
            val infixesFound = mutableListOf<Pair<Int, StandardInfixFunctions>>()
            delimited.forEachIndexed { index, maybeInfix ->
                val infixFunction = StandardInfixFunctions.getInfixFunctionBySymbol(maybeInfix)
                if (infixFunction != null) {
                    infixesFound.add(Pair(index, infixFunction))
                }
            }

            infixesFound.sortByDescending { it.first }
            infixesFound.sortByDescending { it.second.Priority }

            val infixFunction = infixesFound.first().second
            val topLevel = infixesFound.first()

            var indexOfTopLevel: Int = -1
            var level = 0
            var index = 0
            for(character in processedInput) {
                if(character == '(')
                    level++

                if(character == ')')
                    level--

                if(level == 0)
                    if(character == infixFunction.symbol.first() && processedInput.substring(index, index + infixFunction.symbol.length) == infixFunction.symbol) {
                        indexOfTopLevel = index
                        break
                    }

                index++
            }

            return InfixFunction(topLevel.second.symbol, parseFormula(processedInput.substring(0, indexOfTopLevel)), parseFormula(processedInput.substring(indexOfTopLevel + topLevel.second.symbol.length)))
        }

        FormulaType.PREFIX -> {
            val firstParenthesis = processedInput.indexOf('(')
            val name = processedInput.substring(0, firstParenthesis)
            val parenthesis = processedInput.substring(firstParenthesis + 1, processedInput.length - 1)

            var level = 0
            val parameters = mutableListOf<String>()
            var currentParameter = ""

            for(character in parenthesis) {
                if(character == '(')
                    level++

                if(character == ')')
                    level--

                if(character == ',' && level == 0) {
                    parameters.add(currentParameter)
                    currentParameter = ""
                } else {
                    currentParameter += character
                }
            }

            parameters.add(currentParameter)

            return PrefixFunction(name, parameters.map { parseFormula(it) })
        }

        FormulaType.VALUE -> {
            return NumberValue(processedInput.toInt())
        }

        FormulaType.VARIABLE -> {
            return Variable(processedInput)
        }
    }
}


