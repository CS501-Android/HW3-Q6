package com.example.flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.EditText
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val theseQuestions: Questions = Questions()
        theseQuestions.generateAllQuestions()

        val text = findViewById<TextView>(R.id.display)
        val generateButton = findViewById<Button>(R.id.randomQuestions)
        val userAnswer = findViewById<EditText>(R.id.userAnswer)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // When submit button is clicked, test to see if user is done, if not, continue to the next question
        submitButton.setOnClickListener{
            if (!theseQuestions.testIfDone()) {
                val current = theseQuestions.getCurrentQuestion() // the current question
                val userAns = userAnswer.text.toString().toInt() // the users answer

                if (theseQuestions.testCorrectness(userAns, current)) { // if the user input a correct answer, update numCorrect
                    theseQuestions.updateCorrect()
                }

                theseQuestions.updateNumDone() // update the number of questions done by the user

                if (theseQuestions.testIfDone()) { // if the user is done with the questions
                    text.text = theseQuestions.getNumCorrect() // display to the user the number of correct answers
                } else {
                    text.text = theseQuestions.getCurrentQuestion() // otherwise, continue and show next question
                }
            }
        }

        // When generate button is clicked, restart and create 10 new questions only if questions are done
        generateButton.setOnClickListener{
            if (theseQuestions.testIfDone()) {
                theseQuestions.restartQuestions()
                text.text = theseQuestions.getCurrentQuestion()
            }
        }


        text.text = theseQuestions.getCurrentQuestion()

    }
}