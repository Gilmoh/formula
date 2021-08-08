package de.gilmoh

import de.gilmoh.formula.core.PostfixFunction
import de.gilmoh.formula.core.Variable
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ExampleTest {
    @Test
    fun trivialTest() {
        assertEquals(
            PostfixFunction("not", listOf(Variable("a"), Variable("b"))),
            PostfixFunction("not", listOf(Variable("a"), Variable("b")))
        );
    }
}