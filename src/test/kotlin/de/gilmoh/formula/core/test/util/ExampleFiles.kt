package de.gilmoh.formula.core.test.util

enum class ExampleFiles(
    val fileName: String
) {
    QUOTIENT_EQUIVALENCY("quotientEquivalency.formula"),
    DEPTH_EQ_ONE_PLUS_SIZE("depthEqOnePlusSize.formula"),
    TOP_LEVEL_PARENTHESIS("topLevelParenthesis.formula"),
    SUPERDEPTH("superDepth.formula"),
    ASSOCIATIVITY("associativity.formula");


    fun read(): String {
        return readExample(this)
    }
}