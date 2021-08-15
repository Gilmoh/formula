package de.gilmoh.formula.core.models.interfaces

interface Function : Formula {
    val arity: Int
    val parameters: Collection<Formula>
}