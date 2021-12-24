package com.example.c4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.Serializable

class TestActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val test = intent.extras?.get("TEST") as Test

        val question = findViewById<TextView>(R.id.question)
        val theme = findViewById<TextView>(R.id.theme)
        val correctAnswer = Translator().integerTranslator(test.number, test.inputSystem, test.outputSystem)

        question.text = test.question
        theme.text = test.theme

        findViewById<TextView>(R.id.question).setMovementMethod(ScrollingMovementMethod())

        findViewById<Button>(R.id.go).setOnClickListener {

            val answer = findViewById<EditText>(R.id.answer)

            if (correctAnswer == answer.text.toString()) {
                Toast.makeText(this, "Верно!", Toast.LENGTH_LONG).show()
                test.status = "DONE"
                val intent = Intent(this, TestsActivity::class.java)
                setResult(RESULT_OK, intent)
                intent.putExtra("TEST", test as Serializable)
                finish()
            } else
                Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show()

        }

    }
}