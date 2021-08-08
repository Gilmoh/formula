package de.gilmoh.formula.core.models

import de.gilmoh.formula.core.models.interfaces.Formula

data class Priority(
    val inner: Formula,
) : Formula