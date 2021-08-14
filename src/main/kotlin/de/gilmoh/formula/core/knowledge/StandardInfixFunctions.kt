package de.gilmoh.formula.core.knowledge

import de.gilmoh.formula.core.models.interfaces.HasSymbol

enum class StandardInfixFunctions(
    override val symbol: String,
    val Priority: Int,
) : HasSymbol {
    // Arithmetic
    ADDITION("+", 50),
    SUBTRACTION("-", 50),
    MULTIPLICATION("*", 50),
    DIVISION("/", 50),

    EXPONENTIATE("^", 75),

    // Comparision
    EQUALITY("=", 100),
    LESS_THAN("<", 100),
    GREATER_THAN(">", 100),
    LEQ(">=", 100),
    GEQ("=<", 100),
    INEQUALITY("!=", 100),

    // Logic
    AND("and", 125),
    NAND("nand", 125),
    OR("or", 150),
    NOR("nor", 150),
    IMPLIES("=>", 200),
    IS_IMPLIED("<=", 200),
    EQUIVALENCE("<=>", 200),

}

