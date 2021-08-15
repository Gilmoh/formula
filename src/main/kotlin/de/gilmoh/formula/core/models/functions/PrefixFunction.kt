package de.gilmoh.formula.core.models.functions

import de.gilmoh.formula.core.models.interfaces.Formula
import de.gilmoh.formula.core.models.interfaces.Function
import de.gilmoh.formula.core.models.interfaces.HasSymbol

data class PrefixFunction(
    override val symbol: String,
    override val parameters: List<Formula>
) : Function, HasSymbol {
    override val arity: Int
        get() = this.parameters.size

    constructor(symbol: String, vararg parameters: Formula) : this(symbol, parameters.toList())
}