package de.gilmoh.formula.core.test

import de.gilmoh.formula.core.test.util.ExampleFiles
import de.gilmoh.formula.core.test.util.readExample
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class AllExampleFilesExist {

    @Test
    fun testAllExamplesFiles() {
        for(example in ExampleFiles.values())
            example.read()

        assertTrue(true)
    }
}