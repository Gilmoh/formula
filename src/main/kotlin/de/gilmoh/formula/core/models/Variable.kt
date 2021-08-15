package de.gilmoh.formula.core.models

import de.gilmoh.formula.core.models.interfaces.Formula
import de.gilmoh.formula.core.models.interfaces.HasSymbol

data class Variable(
    override val symbol: String
): Formula, HasSymbol