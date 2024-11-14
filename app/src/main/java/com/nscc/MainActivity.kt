package com.nscc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private var input = ""
    private var operator = ""
    private var operand1 = ""
    private var operand2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        displayTextView = findViewById(R.id.displayTextView)
        displayTextView.text = "0"  // Initialize with "0"

        // Number Buttons
        val buttons = arrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonDivide
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                numberPressed(it)
            }
        }

        // Operator Buttons
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { operatorPressed("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { operatorPressed("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { operatorPressed("×") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { operatorPressed("÷") }

        // Clear Button
        findViewById<Button>(R.id.buttonC).setOnClickListener { clear() }

        // Equals Button
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculate() }

        // Settings Button
        findViewById<FloatingActionButton>(R.id.settingsButton).setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun numberPressed(view: View) {
        val button = view as Button
        input += button.text.toString()
        displayTextView.text = input
    }

    private fun operatorPressed(op: String) {
        if (input.isNotEmpty()) {  // Only set operand1 if input is not empty
            operand1 = input
            operator = op
            input = ""
        }
    }

    private fun clear() {
        input = ""
        operand1 = ""
        operand2 = ""
        operator = ""
        displayTextView.text = "0"  // Reset display to "0"
    }

    private fun calculate() {
        operand2 = input
        if (operand1.isNotEmpty() && operand2.isNotEmpty()) {
            val result = when (operator) {
                "+" -> operand1.toDouble() + operand2.toDouble()
                "-" -> operand1.toDouble() - operand2.toDouble()
                "×" -> operand1.toDouble() * operand2.toDouble()
                "÷" -> if (operand2.toDouble() != 0.0) {
                    operand1.toDouble() / operand2.toDouble()
                } else {
                    displayTextView.text = "Error"
                    clear()  // Clear inputs on error
                    return
                }
                else -> return
            }
            displayTextView.text = result.toString()
            input = result.toString()  // Set the input to the result for further operations
        }
    }
}
