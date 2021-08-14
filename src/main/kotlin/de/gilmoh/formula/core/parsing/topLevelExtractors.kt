package de.gilmoh.formula.core.parsing

import de.gilmoh.formula.core.knowledge.StandardInfixFunctions

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
    var level = 1;
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