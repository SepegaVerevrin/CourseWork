package com.example.c4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class TranslatorActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translator)

        findViewById<Button>(R.id.buttonStart).setOnClickListener {

            val stringNumber = findViewById<EditText>(R.id.editTextNumber).text.toString()
            val stringInputNumSystem =
                findViewById<EditText>(R.id.editTextInputNumSystem).text.toString()
            val stringOutputNumSystem =
                findViewById<EditText>(R.id.editTextOutputNumSystem).text.toString()

            if (Regex("^[1-9a-zA-Z]([0-9a-zA-Z]){0,8}").matches(stringNumber) && Regex("^[1-9]([0-9]){0,8}").matches(
                    stringInputNumSystem
                ) && Regex("^[1-9]([0-9]){0,8}").matches(stringOutputNumSystem)
            ) {

                val number = stringNumber
                val inputNumSystem = stringInputNumSystem.toInt()
                val outputNumSystem = stringOutputNumSystem.toInt()

                val res = Translator().integerTranslator(number, inputNumSystem, outputNumSystem)

                if (res == "") Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

                findViewById<TextView>(R.id.textViewRes).text = res
            } else if (Regex("^([0-9a-zA-Z]){1,5}.[1-9a-zA-Z]([0-9a-zA-Z]){0,5}").matches(stringNumber) && Regex("^[1-9]([0-9]){0,8}").matches(stringInputNumSystem) && Regex("^[1-9]([0-9]){0,8}").matches(stringOutputNumSystem)
            ) {
                val number = stringNumber
                val inputNumSystem = stringInputNumSystem.toInt()
                val outputNumSystem = stringOutputNumSystem.toInt()

                val res = Translator().floatTranslator(number, inputNumSystem, outputNumSystem)

                if (res == "") Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

                findViewById<TextView>(R.id.textViewRes).text = res

            }
        }
    }
}