package com.example.calculator

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var header: TextView
    private lateinit var operatingBox: TextView
    private lateinit var valueBox: TextView
    private var isNewValue: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        header = findViewById(R.id.header)
        operatingBox = findViewById(R.id.operatingBox)
        valueBox = findViewById(R.id.valueBox)

        val buttonCE: Button = findViewById(R.id.buttonCE)
        val buttonC: Button = findViewById(R.id.buttonC)
        val buttonBS: Button = findViewById(R.id.buttonBS)

        buttonCE.setOnClickListener { clearEntry() }
        buttonC.setOnClickListener { clear() }
        buttonBS.setOnClickListener { backspace() }

        // Initialize operatingBox and valueBox as empty
        operatingBox.text = ""
        valueBox.text = ""

        val numberButtons = arrayOf(
            findViewById<Button>(R.id.button0), findViewById<Button>(R.id.button1), findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3), findViewById<Button>(R.id.button4), findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6), findViewById<Button>(R.id.button7), findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9) )
        for (button in numberButtons) {
            button.setOnClickListener {
                if (!isNewValue) {
                    valueBox.text = "${valueBox.text}${(it as Button).text}"
                } else {
                    valueBox.text = "${(it as Button).text}"
                    isNewValue = false
                }
            }
        }

        val operationButtons = arrayOf(
            findViewById<Button>(R.id.addition), findViewById<Button>(R.id.subtract),
            findViewById<Button>(R.id.multiply), findViewById<Button>(R.id.divide) )
        for (button in operationButtons) {
            button.setOnClickListener {
                operatingBox.text = "${valueBox.text} ${(it as Button).text}"
                valueBox.text = ""
            }
        }

        val equalsButton: Button = findViewById(R.id.equals)
        equalsButton.setOnClickListener {
            // Calculate the result of the operation and display it in valueBox
            valueBox.text = calculateResult(operatingBox.text.toString(), valueBox.text.toString())
            operatingBox.text = ""
            isNewValue = true
        }
    }

    private fun clearEntry() {
        // Clear the current entry
        valueBox.text = ""
    }

    private fun clear() {
        // Clear all entries and operations
        operatingBox.text = ""
        valueBox.text = ""
    }

    private fun backspace() {
        // Remove the last character from the current entry
        if (valueBox.text.isNotEmpty()) {
            valueBox.text = valueBox.text.dropLast(1)
        }
    }

    private fun calculateResult(operation: String, value: String): String {
        // Calculate the result of the operation and return it as a string
        val operationParts = operation.split(" ")
        if (operationParts.size < 2) return "Error"

        val operand1 = operationParts[0].toDoubleOrNull() ?: return "Error"
        val operator = operationParts[1]
        val operand2 = value.toDoubleOrNull() ?: return "Error"

        return when (operator) {
            "+" -> (operand1 + operand2).toString()
            "-" -> (operand1 - operand2).toString()
            "x" -> (operand1 * operand2).toString()
            "/" -> if (operand2 != 0.0) {
                (operand1 / operand2).toString()
            } else {
                "Error"
            }
            else -> "Error"
        }
    }

}
