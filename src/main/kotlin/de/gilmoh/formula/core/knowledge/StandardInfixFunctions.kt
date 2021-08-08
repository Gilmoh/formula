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


}

