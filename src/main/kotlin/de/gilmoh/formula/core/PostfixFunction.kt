package de.gilmoh.formula.core

data class PostfixFunction(
    val symbol: String,
    val parameters: List<Formula>
) : Formula