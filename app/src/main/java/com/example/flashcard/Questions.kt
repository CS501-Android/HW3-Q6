package com.example.flashcard

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.math.floor
import kotlin.math.roundToInt

class Questions : ViewModel() {
    private var numCorrect: Int = 0 // track the number of correct answers the user has given
    private var listOfQuestions = mutableListOf<String>() // the list of questions is just a list of string operation questions
    private var numDone: Int = 0 // track the number of questions the user has completed

    // Generate all of the questions of subtraction or addition for the user
    fun generateAllQuestions() {
        for (i in 0..9) { // iterate through 10 times
            listOfQuestions.add(generateOneQuestion()) // add a generated question to the list
        }
    }

    // Generate a single question of subtraction or addition
    private fun generateOneQuestion(): String {
        val operatorOne = (Math.random() * 98 + 1).roundToInt() // The first operator is in the range {1, 2, 3, ..., 99}
        val operatorTwo = (Math.random() * 19 + 1).roundToInt() // The second operator is in the range {1, 2, 3, ..., 20}
        val questionType = if (Math.random() > .5) "sub" else "add" // 50-50 chance of subtraction and addition

        if (questionType == "sub") { // if the question is a subtraction question, return One minus Two
            return operatorOne.toString().plus(" - ").plus(operatorTwo.toString())
        }

        // otherwise, return a string of One plus Two
        return operatorOne.toString().plus(" + ").plus(operatorTwo.toString())
    }

    // Test to see if user has given a correct answer
    fun testCorrectness(i: Int, q: String): Boolean {
        if (q.contains("-")) { // if string contains "-", it's a sub problem: see if One - Two is answer given
            val split = q.split("-")
            if ((split[0].replace(" ", "").toInt()-split[1].replace(" ", "").toInt()) == i) {
                return true // if it is equal to given answer, return true
            }
        } else { // otherwise, string contains a "+", so its an addition problem: see if One + Two is answer given
            val split = q.split("+")
            if (split[0].replace(" ", "").toInt()+split[1].replace(" ", "").toInt() == i) {
                return true
            }
        }

        // if we get here, the user has input the wrong answer so return false
        return false
    }

    fun restartQuestions() {
        this.listOfQuestions.clear()
        this.numCorrect = 0
        this.numDone = 0
        generateAllQuestions()
    }

    // Get the question which is the first element in the list
    fun getCurrentQuestion(): String {
        return listOfQuestions[getNumDone()]
    }

    // Test to see if list is empty: return true if it is, false otherwise
    fun testIfEmpty(): Boolean {
        return listOfQuestions.isEmpty()
    }

    fun getListOfQuestions(): MutableList<String> {
        return this.listOfQuestions
    }

    fun setListOfQuestions(x: MutableList<String>) {
        this.listOfQuestions = x
    }

    // Remove the first element in the list
    fun remFirstQuestion() {
        listOfQuestions.removeFirst()
    }

    // Update the number of correct answers given that the user has answered correctly
    fun updateCorrect() {
        this.numCorrect += 1
    }

    fun updateNumDone() {
        this.numDone += 1
    }

    // Return true if the user has done 10 questions
    fun testIfDone(): Boolean {
        return getNumDone() >= 10
    }

    // Return the number of questions done by the user
    fun getNumDone(): Int {
        return this.numDone
    }

    // Get the number of correct answers as an integer
    fun getNumCorrectInt(): Int {
        return this.numCorrect
    }

    // Set the number of completed questions
    fun setNumComplete(x: Int) {
        this.numDone = x
    }

    fun setNumCorrect(x: Int) {
        this.numCorrect = x
    }

    // Get the number of correct answers
    fun getNumCorrect(): String {
        return this.numCorrect.toString().plus(" out of 10")
    }
}
