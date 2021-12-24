package com.example.c4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.*
import java.io.Serializable

class SelectTestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_test)

        val test = intent.extras?.get("TEST") as Test

        val question = findViewById<TextView>(R.id.question)
        val theme = findViewById<TextView>(R.id.theme)

        question.text = test.question
        theme.text = test.theme

        findViewById<TextView>(R.id.question).setMovementMethod(ScrollingMovementMethod())

        if (test.type=="testSelectTheoreticalAnswer") {

            val answers = test.number.split(";").toList()
            val numCorrectAnswer=test.outputSystem
            val firstAnswerCheckBox = findViewById<CheckBox>(R.id.firstAnswerCheckBox)
            firstAnswerCheckBox.text=answers[0]
            val secondAnswerCheckBox = findViewById<CheckBox>(R.id.secondAnswerCheckBox)
            secondAnswerCheckBox.text=answers[1]
            val thirdAnswerCheckBox = findViewById<CheckBox>(R.id.thirdAnswerCheckBox)
            thirdAnswerCheckBox.text=answers[2]

            findViewById<Button>(R.id.go).setOnClickListener {
                when (numCorrectAnswer) {
                    0 -> if (firstAnswerCheckBox.isChecked && !secondAnswerCheckBox.isChecked && !thirdAnswerCheckBox.isChecked) {
                        Toast.makeText(this, "Верно!", Toast.LENGTH_LONG).show()
                        test.status = "DONE"
                        val intent = Intent(this, TestsActivity::class.java)
                        setResult(RESULT_OK, intent)
                        intent.putExtra("TEST", test as Serializable)
                        finish()
                    } else Toast.makeText(this, "Не верно.", Toast.LENGTH_LONG).show()
                    1 -> if (!firstAnswerCheckBox.isChecked && secondAnswerCheckBox.isChecked && !thirdAnswerCheckBox.isChecked) {
                        Toast.makeText(this, "Верно!", Toast.LENGTH_LONG).show()
                        test.status = "DONE"
                        val intent = Intent(this, TestsActivity::class.java)
                        setResult(RESULT_OK, intent)
                        intent.putExtra("TEST", test as Serializable)
                        finish()
                    } else Toast.makeText(this, "Не верно.", Toast.LENGTH_LONG).show()
                    2 -> if (!firstAnswerCheckBox.isChecked && !secondAnswerCheckBox.isChecked && thirdAnswerCheckBox.isChecked) {
                        Toast.makeText(this, "Верно!", Toast.LENGTH_LONG).show()
                        test.status = "DONE"
                        val intent = Intent(this, TestsActivity::class.java)
                        setResult(RESULT_OK, intent)
                        intent.putExtra("TEST", test as Serializable)
                        finish()
                    } else Toast.makeText(this, "Не верно.", Toast.LENGTH_LONG).show()
                }
            }
        } else if (test.type=="testSelectCalculatingAnswer") {

            val answers = test.number.split(";").toList()
            val numCorrectAnswer=test.outputSystem
            val firstAnswerCheckBox = findViewById<CheckBox>(R.id.firstAnswerCheckBox)
            firstAnswerCheckBox.text=answers[0]
            val secondAnswerCheckBox = findViewById<CheckBox>(R.id.secondAnswerCheckBox)
            secondAnswerCheckBox.text=answers[1]
            val thirdAnswerCheckBox = findViewById<CheckBox>(R.id.thirdAnswerCheckBox)
            thirdAnswerCheckBox.text=answers[2]

            val correctAnswer = Translator().integerTranslator(test.number, test.inputSystem, test.outputSystem)

            findViewById<Button>(R.id.go).setOnClickListener {

                when (numCorrectAnswer) {
                    0 -> if (firstAnswerCheckBox.isChecked && !secondAnswerCheckBox.isChecked && !thirdAnswerCheckBox.isChecked) {
                        Toast.makeText(this, "Верно!", Toast.LENGTH_LONG).show()
                        test.status = "DONE"
                        val intent = Intent(this, TestsActivity::class.java)
                        setResult(RESULT_OK, intent)
                        intent.putExtra("TEST", test as Serializable)
                        finish()
                    } else Toast.makeText(this, "Не верно.", Toast.LENGTH_LONG).show()
                    1 -> if (!firstAnswerCheckBox.isChecked && secondAnswerCheckBox.isChecked && !thirdAnswerCheckBox.isChecked) {
                        Toast.makeText(this, "Верно!", Toast.LENGTH_LONG).show()
                        test.status = "DONE"
                        val intent = Intent(this, TestsActivity::class.java)
                        setResult(RESULT_OK, intent)
                        intent.putExtra("TEST", test as Serializable)
                        finish()
                    } else Toast.makeText(this, "Не верно.", Toast.LENGTH_LONG).show()
                    2 -> if (!firstAnswerCheckBox.isChecked && !secondAnswerCheckBox.isChecked && thirdAnswerCheckBox.isChecked) {
                        Toast.makeText(this, "Верно!", Toast.LENGTH_LONG).show()
                        test.status = "DONE"
                        val intent = Intent(this, TestsActivity::class.java)
                        setResult(RESULT_OK, intent)
                        intent.putExtra("TEST", test as Serializable)
                        finish()
                    } else Toast.makeText(this, "Не верно.", Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}