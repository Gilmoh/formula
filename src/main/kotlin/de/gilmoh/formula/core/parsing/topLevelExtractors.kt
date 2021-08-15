package de.gilmoh.formula.core.parsing

import de.gilmoh.formula.core.knowledge.StandardInfixFunctions
import java.lang.RuntimeException

enum class FormulaType {
    PRIORITY,
    PREFIX,
    INFIX,
    VARIABLE,
    VALUE;
}

fun getTopLevelFunctionType(input: String): FormulaType {
    val processedInput = preprocessInput(input)
    if(isTopLevelPriority(processedInput))
        return FormulaType.PRIORITY

    val inputWithoutParenthesisContent = removeContentOfParenthesis(processedInput)

    val delimited  = inputWithoutParenthesisContent.split(" ")

    val anyInfix = delimited.any {
        isTopLevelFunctionInfix(it)
    }
    if(anyInfix)
        return FormulaType.INFIX

    if(delimited.size == 1) {
        val maybePrefix = delimited[0]
        var isPrefix = true

        isPrefix = isPrefix && maybePrefix.endsWith("()")
        for (character in maybePrefix.dropLast(2)) {
            isPrefix = isPrefix && isValidCharacter(character)
        }

        if(isPrefix)
            return FormulaType.PREFIX
    }

    if(delimited.size == 1 && delimited[0].any { it.isLetter() })
        return FormulaType.VARIABLE

    if(delimited.size == 1 && delimited[0].all { it.isDigit() })
        return FormulaType.VALUE


    throw RuntimeException("No Formula Type could be assigned to ${processedInput}.")
}


fun isTopLevelFunctionPrefix(input: String): Boolean {
    val processedInput = preprocessInput(input)

    if(!processedInput.endsWith(')'))
        return false

    var level = 0
    var parameterSection = false
    var functionName = ""
    var closedOuterParenthesis = false

    for(character in processedInput) {
        if(!parameterSection) {
            if(character == '(') {
                parameterSection = true
                if(functionName.isEmpty())
                    return false
            } else if(isValidCharacter(character)) {
                functionName += character
                continue
            } else {
                return false
            }
        }

        if(character == '(')
            level++
        else if(character == ')')
            level--

        if(level == 0) {
            if(closedOuterParenthesis)
                return false
            closedOuterParenthesis = true
        }

        if(level < 0)
            return false

    }

    return level == 0
}

fun isValidCharacter(character: Char): Boolean {
    return character.isLetter()
}

fun isTopLevelPriority(input: String): Boolean {
    val processedInput = preprocessInput(input)
    if(!(processedInput.startsWith("(") && processedInput.endsWith(")")))
        return false


    // Setting up this way to simplify the preemptive check
    var level = 1
    for(character in processedInput.drop(1)) {
        if(level == 0)
            return false
        when(character) {
            '(' -> level++
            ')' -> level--
        }
    }

    return level == 0
}

fun isTopLevelFunctionInfix(input: String): Boolean {
    val infixFunctions = StandardInfixFunctions.values().map { it.symbol }.toSet()
    val processedInput = preprocessInput(input)

    var level = 0
    var inside = false
    var contentWithoutParenthesis = ""

    for (character in processedInput) {
        if(character == '(') {
            inside = true
            level++
        }

        if(!inside)
            contentWithoutParenthesis += character

        if(character == ')') {
            level--
            if (level == 0)
                inside = false
        }

    }

    infixFunctions.forEach { infix ->
        if(contentWithoutParenthesis.contains(infix))
            return true
    }

    return false
}

fun topLevelParenthesis(input: String): List<String> {
    val processedInput = preprocessInput(input)

    var inside = false
    var level = 0
    var currentContent = ""
    val topLevelParenthesis: MutableList<String> = mutableListOf()

    for (character in processedInput) {
        if(character == '(') {
            inside = true
            level++
        }

        if(inside)
            currentContent += character


        if(character == ')') {
            level--
        }

        if(level == 0)
            inside = false

        val processedCurrentContent = preprocessInput(currentContent)

        if(level == 0 && character == ')' && processedCurrentContent != "") {
            topLevelParenthesis.add(processedCurrentContent)
            currentContent = ""
        }
    }

    return topLevelParenthesis.toList()
}

fun preprocessInput(input: String): String {
    return input.trim()
}

fun removeContentOfParenthesis(input: String): String {
    var inputWithoutParenthesisContent = ""
    var level = 0

    for (character in input) {
        if(character == '(') {
            if(level == 0)
                inputWithoutParenthesisContent += character
            level++

            continue
        }

        if(character == ')') {
            level--
            if(level == 0)
                inputWithoutParenthesisContent += character

            continue
        }

        if(level == 0)
            inputWithoutParenthesisContent += character

    }

    return inputWithoutParenthesisContent
}