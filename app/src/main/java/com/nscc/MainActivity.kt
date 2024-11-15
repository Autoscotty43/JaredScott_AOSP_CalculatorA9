// Author: Jared Scott
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
    private var input = "" // Stores current input
    private var operator = "" // Stores the current operator
    private var operand1 = "" // Stores the first operand
    private var operand2 = "" // Stores the second operand

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide action bar to make it a full-screen app
        supportActionBar?.hide()

        // Initialize displayTextView with "0"
        displayTextView = findViewById(R.id.displayTextView)
        displayTextView.text = "0"

        // Array of number buttons, adding click listeners
        val buttons = arrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        )
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                numberPressed(it)
            }
        }

        // Operator buttons with respective operators
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { operatorPressed("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { operatorPressed("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { operatorPressed("×") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { operatorPressed("÷") }

        // Clear button resets all values
        findViewById<Button>(R.id.buttonC).setOnClickListener { clear() }

        // Equals button calculates the result
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculate() }

        // Settings button opens the SettingsActivity
        findViewById<FloatingActionButton>(R.id.settingsButton).setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    // Method to handle number button clicks
    private fun numberPressed(view: View) {
        val button = view as Button
        input += button.text.toString() // Append the number to input
        displayTextView.text = input // Update display
    }

    // Method to handle operator button clicks
    private fun operatorPressed(op: String) {
        if (input.isNotEmpty()) {
            operand1 = input // Set first operand
            operator = op // Set operator
            input = "" // Clear input for next operand
        }
    }

    // Method to clear all input and reset calculator
    private fun clear() {
        input = ""
        operand1 = ""
        operand2 = ""
        operator = ""
        displayTextView.text = "0" // Reset display to "0"
    }

    // Method to calculate the result with exception handling
    private fun calculate() {
        operand2 = input
        if (operand1.isNotEmpty() && operand2.isNotEmpty()) {
            try {
                // Perform calculation based on the operator
                val result = when (operator) {
                    "+" -> operand1.toDouble() + operand2.toDouble()
                    "-" -> operand1.toDouble() - operand2.toDouble()
                    "×" -> operand1.toDouble() * operand2.toDouble()
                    "÷" -> if (operand2.toDouble() != 0.0) {
                        operand1.toDouble() / operand2.toDouble()
                    } else {
                        displayTextView.text = "Error: Division by zero"
                        clear() // Reset on error
                        return
                    }
                    else -> return
                }
                // Display result and set it as input for further calculations
                displayTextView.text = result.toString()
                input = result.toString()
            } catch (e: NumberFormatException) {
                // Handle number format error
                displayTextView.text = "Error: Invalid number format"
                clear()
            } catch (e: ArithmeticException) {
                // Handle arithmetic error
                displayTextView.text = "Error: Calculation error"
                clear()
            } catch (e: Exception) {
                // Handle unexpected errors
                displayTextView.text = "Unexpected error occurred"
                clear()
            }
        }
    }
}