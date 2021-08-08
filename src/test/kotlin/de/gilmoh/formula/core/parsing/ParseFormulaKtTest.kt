package de.gilmoh.formula.core.parsing

import de.gilmoh.formula.core.models.Variable
import de.gilmoh.formula.core.models.functions.InfixFunction
import de.gilmoh.formula.core.test.util.ExampleFiles
import de.gilmoh.formula.core.test.util.readExample
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ParseFormulaKtTest {

    @Test
    fun testQuotientEquivalence() {
        assertEquals(
            parseFormula(readExample(ExampleFiles.QUOTIENT_EQUIVALENCY)),
            InfixFunction("<=>",
                InfixFunction("=",
                    InfixFunction("/", Variable("a"), Variable("b")),
                    InfixFunction("/", Variable("c"), Variable("d")),
                ),
                InfixFunction("=",
                    InfixFunction("*", Variable("a"), Variable("d")),
                    InfixFunction("*", Variable("b"), Variable("c"))
                )
            )

        )
    }

}