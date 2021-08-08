package de.gilmoh.formula.core.test.util

import java.io.File

fun readExample(example: String): String
    = File("src/test/resources/examples/${example}").readText()

fun readExample(example: ExampleFiles): String = readExample(example.fileName)