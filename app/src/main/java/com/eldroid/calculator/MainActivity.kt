package com.eldroid.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var operand1: Double? = null
    private var operator: String? = null
    private var isNewOperation: Boolean = true
    private var hasDecimal: Boolean = false // Track if the current operand has a decimal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Number buttons
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2,
            R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8,
            R.id.button9
        )

        buttons.forEachIndexed { index, buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                appendToDisplay(index.toString())
            }
        }

        // Decimal button
        findViewById<Button>(R.id.buttonDecimal).setOnClickListener {
            if (!hasDecimal) {
                if (isNewOperation) {
                    display.text = "" // Start fresh if it's a new operation
                    isNewOperation = false
                }
                appendToDisplay(".")
                hasDecimal = true
            }
        }

        // Operator buttons
        val operators = mapOf(
            R.id.buttonAdd to "+",
            R.id.buttonSubtract to "-",
            R.id.buttonMultiply to "x",
            R.id.buttonDivide to "/"
        )

        operators.forEach { (buttonId, op) ->
            findViewById<Button>(buttonId).setOnClickListener {
                if (operator == null && display.text.isNotEmpty()) {
                    operand1 = display.text.toString().toDoubleOrNull()
                    operator = op
                    appendToDisplay(op)
                    isNewOperation = false
                    hasDecimal = false // Reset decimal tracking for the next operand
                }
            }
        }

        // Equals button
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculateResult() }

        // Clear button
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clearDisplay() }
    }

    @SuppressLint("SetTextI18n")
    private fun appendToDisplay(value: String) {
        if (isNewOperation) {
            display.text = "" // Clear display for new operation
            isNewOperation = false
        }
        display.append(value) // Append the value
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun calculateResult() {
        if (operand1 != null && operator != null) {
            // Get the second operand
            val inputText = display.text.toString()
            val operand2String = inputText.substringAfterLast(operator!!).trim()
            val operand2 = operand2String.toDoubleOrNull()

            if (operand2 != null) {
                val result = when (operator) {
                    "+" -> operand1!! + operand2
                    "-" -> operand1!! - operand2
                    "x" -> operand1!! * operand2 // Corrected multiplication
                    "/" -> if (operand2 != 0.0) operand1!! / operand2 else {
                        display.text = "Error"
                        resetCalculator()
                        return
                    }
                    else -> 0.0
                }

                // Display the result with two decimal places
                display.text = String.format("%.2f", result)

                resetCalculator() // Reset the calculator state after calculation
            }
        }
    }


    private fun clearDisplay() {
        display.text = ""
        resetCalculator()
    }

    private fun resetCalculator() {
        operand1 = null
        operator = null
        isNewOperation = true // Reset to allow a new operation
        hasDecimal = false // Reset decimal flag for the next input
    }
}
