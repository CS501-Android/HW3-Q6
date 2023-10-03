package com.example.flashcard

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import java.lang.Exception

var loginCredentials = ""

class MainActivity : AppCompatActivity() {
    private val theseQuestions: Questions = Questions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (loginCredentials == "") {
            setContentView(R.layout.activity_login)

            val userField = findViewById<EditText>(R.id.usernameField)
            val loginButton = findViewById<Button>(R.id.loginButton)

            loginButton.setOnClickListener{
                loginCredentials = userField.text.toString()
                setContentView(R.layout.activity_main)

                val userText = findViewById<TextView>(R.id.greetUser)
                userText.text = "Hi ".plus(loginCredentials)


                theseQuestions.generateAllQuestions()

                val text = findViewById<TextView>(R.id.display)
                val generateButton = findViewById<Button>(R.id.randomQuestions)
                val userAnswer = findViewById<EditText>(R.id.username)
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
        } else {
            setContentView(R.layout.activity_main)
            theseQuestions.restartQuestions()
            findViewById<TextView>(R.id.display).text = theseQuestions.getCurrentQuestion()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("loginCredentials", loginCredentials)
        outState.putInt("numCorrect", theseQuestions.getNumCorrectInt())
        outState.putInt("numComplete", theseQuestions.getNumDone())
        outState.putStringArrayList("listQuestions", ArrayList(theseQuestions.getListOfQuestions()))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val numberCorrect = savedInstanceState.getInt("numCorrect", 0)
        theseQuestions.setNumCorrect(numberCorrect)

        val numberDone = savedInstanceState.getInt("numComplete", 0)
        theseQuestions.setNumComplete(numberDone)

        val listOfQuest = savedInstanceState.getStringArrayList("listQuestions")?.toMutableList()
        if (listOfQuest != null) {
            theseQuestions.setListOfQuestions(listOfQuest.toMutableList())
        }

        val userName = savedInstanceState.getString("loginCredentials", "")
        loginCredentials = "Hi ".plus(userName)
    }
}
