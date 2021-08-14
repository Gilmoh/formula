package de.gilmoh.formula.core.parsing

import de.gilmoh.formula.core.models.NumberValue
import de.gilmoh.formula.core.models.Priority
import de.gilmoh.formula.core.models.Variable
import de.gilmoh.formula.core.models.functions.InfixFunction
import de.gilmoh.formula.core.models.functions.PrefixFunction
import de.gilmoh.formula.core.test.util.ExampleFiles
import de.gilmoh.formula.core.test.util.readExample
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ParseFormulaKtTest {

    @Test
    fun `works with quotientEquivalency`() {

        val expectations = mapOf(
            ExampleFiles.DEPTH_EQ_ONE_PLUS_SIZE to InfixFunction("=",
                PrefixFunction("depth", PrefixFunction("c", Variable("x"), Variable("y"))),
                InfixFunction("+",
                    NumberValue(1),
                    PrefixFunction("size", PrefixFunction("c", Variable("x"), Variable("y")))
                )
            ),
            ExampleFiles.TOP_LEVEL_PARENTHESIS to Priority(
                InfixFunction("=",
                    Variable("x"),
                    PrefixFunction("zetta", PrefixFunction("c", Variable("x"), Variable("y")))
                )
            ),
            ExampleFiles.SUPERDEPTH to PrefixFunction("superdepth",
                PrefixFunction("c", Variable("x"), Variable("y")),
                PrefixFunction("d", Variable("x"), Variable("y"), Variable("z"))
            ),
            ExampleFiles.QUOTIENT_EQUIVALENCY to InfixFunction("<=>",
                InfixFunction("=",
                    InfixFunction("/", Variable("a"), Variable("b")),
                    InfixFunction("/", Variable("c"), Variable("d")),
                ),
                InfixFunction("=",
                    InfixFunction("*", Variable("a"), Variable("d")),
                    InfixFunction("*", Variable("b"), Variable("c"))
                )
            ),
            ExampleFiles.ASSOCIATIVITY to InfixFunction("=",
                InfixFunction("+",
                    Priority(InfixFunction("+", Variable("a"), Variable("b"))),
                    Variable("c")
                ),
                InfixFunction("+",
                    Variable("a"),
                    Priority(InfixFunction("+", Variable("b"), Variable("c")))
                ),
            )
        )

        for((example, expected) in expectations) {
            val actual = parseFormula(example.read())
            assertEquals(expected, actual, "Test failed for ${example.fileName}: ${example.read()}.")
        }
    }

    @Test fun `topLevelParenthesis() works`() {
        val expectations = mapOf(
            ExampleFiles.DEPTH_EQ_ONE_PLUS_SIZE to listOf("(c(x,y))", "(c(x,y))"),
            ExampleFiles.TOP_LEVEL_PARENTHESIS to listOf("(x = zetta(c(x,y)))"),
            ExampleFiles.SUPERDEPTH to listOf("(c(x,y), d(x,y,z))"),
            ExampleFiles.QUOTIENT_EQUIVALENCY to listOf(),
            ExampleFiles.ASSOCIATIVITY to listOf("(a + b)", "(b + c)"),
        )
        for ((example, expected) in expectations) {
            val actual = topLevelParenthesis(example.read())
            assertEquals(expected, actual, "Test failed for ${example.fileName}: ${example.read()}.")
        }

    }

    @Test fun `isTopLevelPriority() works`() {
        val expectations = mapOf(
            ExampleFiles.DEPTH_EQ_ONE_PLUS_SIZE to false,
            ExampleFiles.TOP_LEVEL_PARENTHESIS to true,
            ExampleFiles.SUPERDEPTH to false,
            ExampleFiles.QUOTIENT_EQUIVALENCY to false,
            ExampleFiles.ASSOCIATIVITY to false,
        )
        for ((example, expected) in expectations) {
            val actual = isTopLevelPriority(example.read())
            assertEquals(expected, actual, "Test failed for ${example.fileName}: ${example.read()}.")
        }
    }

    @Test fun `isTopLevelFunctionInfix() works`() {
        val expectations = mapOf(
            ExampleFiles.DEPTH_EQ_ONE_PLUS_SIZE to true,
            ExampleFiles.TOP_LEVEL_PARENTHESIS to false,
            ExampleFiles.SUPERDEPTH to false,
            ExampleFiles.QUOTIENT_EQUIVALENCY to true,
            ExampleFiles.ASSOCIATIVITY to true,
        )
        for ((example, expected) in expectations) {
            val actual = isTopLevelFunctionInfix(example.read())
            assertEquals(expected, actual, "Test failed for ${example.fileName}: ${example.read()}.")
        }
    }

    @Test fun `isTopLevelFunctionPrefix() works`() {
        val expectations = mapOf(
            ExampleFiles.DEPTH_EQ_ONE_PLUS_SIZE to false,
            ExampleFiles.TOP_LEVEL_PARENTHESIS to false,
            ExampleFiles.SUPERDEPTH to true,
            ExampleFiles.QUOTIENT_EQUIVALENCY to false,
            ExampleFiles.ASSOCIATIVITY to false,
        )
        for ((example, expected) in expectations) {
            val actual = isTopLevelFunctionPrefix(example.read())
            assertEquals(expected, actual, "Test failed for ${example.fileName}: ${example.read()}.")
        }
    }

}