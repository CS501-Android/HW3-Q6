package com.example.flashcard

import org.hamcrest.core.AnyOf
import org.junit.Test

import org.junit.Assert.*
import kotlin.reflect.typeOf

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class QuestionsUnitTest {
    private lateinit var questions: Questions
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun questionNotDone() {
        questions = Questions()
        questions.generateAllQuestions()
        assertEquals(false, questions.testIfDone())
    }

    @Test
    fun questionDoneOnce() {
        questions = Questions()
        questions.updateCorrect()
        assertEquals("1 out of 10", questions.getNumCorrect())
    }

    @Test
    fun questionDone() {
        questions = Questions()
        questions.generateAllQuestions()
        for (i in 1..10) {
            questions.updateNumDone()
        }
        assertEquals(true, questions.testIfDone())
    }

}