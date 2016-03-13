package com.ivonneroberts.todo.generator;

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.assertTrue

public class ConstantsGeneratorTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder()

    @Test
    public void testGenerator() {
        def outputDir = tempFolder.newFolder()
        ConstantsGenerator.generateConstantsClass(outputDir)
        def generatedFile = new File(outputDir, 'com/ivonneroberts/todo/Constants.java')
        assertTrue(generatedFile.isFile())

        generatedFile.eachLine { line ->
          println line
        }
    }
}
