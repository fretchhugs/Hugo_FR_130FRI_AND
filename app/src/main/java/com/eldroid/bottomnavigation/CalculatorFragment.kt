package com.eldroid.bottomnavigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CalculatorFragment : Fragment() {
    private lateinit var display: TextView
    private var operand1: Double? = null
    private var operator: String? = null
    private var isNewOperation: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        display = view.findViewById(R.id.display)

        if (savedInstanceState != null) {
            display.text = savedInstanceState.getString("display_text")
            operand1 = savedInstanceState.getDouble("operand1")
            operator = savedInstanceState.getString("operator")
            isNewOperation = savedInstanceState.getBoolean("isNewOperation")
        }

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2,
            R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8,
            R.id.button9
        )

        buttons.forEachIndexed { index, buttonId ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                appendToDisplay(index.toString())
            }
        }

        val operators = mapOf(
            R.id.buttonDecimal to ".",
            R.id.buttonAdd to "+",
            R.id.buttonSubtract to "-",
            R.id.buttonMultiply to "*",
            R.id.buttonDivide to "/"
        )

        // Handle decimal and operator clicks
        operators.forEach { (buttonId, op) ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                when (op) {
                    "." -> if (!display.text.contains(".") || operator != null) {
                        // Only append decimal if no decimal exists or after an operator
                        appendToDisplay(op)
                    }
                    else -> {
                        // Handle operators
                        if (operator == null && display.text.isNotEmpty()) {
                            operand1 = display.text.toString().toDoubleOrNull()
                            if (operand1 != null) {
                                operator = op
                                appendToDisplay(op)
                                isNewOperation = false
                            }
                        }
                    }
                }
            }
        }

        view.findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculateResult() }
        view.findViewById<Button>(R.id.buttonClear).setOnClickListener { clearDisplay() }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("display_text", display.text.toString())
        operand1?.let { outState.putDouble("operand1", it) }
        outState.putString("operator", operator)
        outState.putBoolean("isNewOperation", isNewOperation)
    }

    @SuppressLint("SetTextI18n")
    private fun appendToDisplay(value: String) {
        if (isNewOperation) {
            display.text = ""
            isNewOperation = false
        }
        display.append(value)
    }

    @SuppressLint("SetTextI18n")
    private fun calculateResult() {
        if (operand1 != null && operator != null) {
            val inputText = display.text.toString()
            val operand2String = inputText.substringAfter(operator!!).trim()
            val operand2 = operand2String.toDoubleOrNull()

            if (operand2 != null) {
                val result = when (operator) {
                    "+" -> operand1!! + operand2
                    "-" -> operand1!! - operand2
                    "*" -> operand1!! * operand2
                    "/" -> if (operand2 != 0.0) operand1!! / operand2 else {
                        display.text = "Error"
                        resetCalculator()
                        return
                    }
                    else -> 0.0
                }
                display.text = String.format("%.2f", result)
                resetCalculator()
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
        isNewOperation = true
    }
}
