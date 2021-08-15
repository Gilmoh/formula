package de.gilmoh.formula.core.models.functions

import de.gilmoh.formula.core.models.interfaces.Formula
import de.gilmoh.formula.core.models.interfaces.Function
import de.gilmoh.formula.core.models.interfaces.HasSymbol


data class InfixFunction(
    override val symbol: String,
    val left: Formula,
    val right: Formula,
) : Function, HasSymbol {
    override val arity: Int
        get() = 2

    override val parameters: Collection<Formula>
        get() = listOf(left, right)
}