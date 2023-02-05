package com.example.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.DecimalFormat

enum class CalculatorMode {
    None,Add,Subtract,Multiply
}

class MainActivity : AppCompatActivity() {
    var lastButtonWasMode = false
    var currentMode = CalculatorMode.None
    var labelString = ""
    var savedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCalculator()
    }

    fun setupCalculator() {
        val button0 = findViewById<TextView>(R.id.button0)
        val button1 = findViewById<TextView>(R.id.button1)
        val button2 = findViewById<TextView>(R.id.button2)
        val button3 = findViewById<TextView>(R.id.button3)
        val button4 = findViewById<TextView>(R.id.button4)
        val button5 = findViewById<TextView>(R.id.button5)
        val button6 = findViewById<TextView>(R.id.button6)
        val button7 = findViewById<TextView>(R.id.button7)
        val button8 = findViewById<TextView>(R.id.button8)
        val button9 = findViewById<TextView>(R.id.button9)

        val allButtons = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        for(i in allButtons.indices) {
            allButtons[i].setOnClickListener { didPressNumber(i)}
        }

        val buttonPlus = findViewById<TextView>(R.id.buttonPlus)
        val buttonMinus = findViewById<TextView>(R.id.buttonMinus)
        val buttonMultiply = findViewById<TextView>(R.id.buttonMultiply)
        val buttonEquals = findViewById<TextView>(R.id.buttonEquals)
        val buttonC = findViewById<TextView>(R.id.buttonC)

        buttonPlus.setOnClickListener {changeMode(CalculatorMode.Add)}
        buttonMinus.setOnClickListener {changeMode(CalculatorMode.Subtract)}
        buttonMultiply.setOnClickListener { changeMode(CalculatorMode.Multiply) }
        buttonEquals.setOnClickListener { didPressEquals()}
        buttonC.setOnClickListener { didPressClear()}

    }

    fun didPressEquals() {
        if (lastButtonWasMode) {
            return
        }

        val labelInt = labelString.toInt()

        when(currentMode) {
            CalculatorMode.Add -> savedNum += labelInt
            CalculatorMode.Subtract -> savedNum -= labelInt
            CalculatorMode.Multiply -> savedNum *= labelInt
            CalculatorMode.None -> return

        }

        currentMode = CalculatorMode.None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true




    }

    fun didPressClear() {
        lastButtonWasMode = false
        currentMode = CalculatorMode.None
        labelString =""
        savedNum = 0
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "0"
    }

    @SuppressLint("SetTextI18n")
    fun updateText() {

        if(labelString.length > 8){
            didPressClear()
            val textView = findViewById<TextView>(R.id.textView)
            textView.text = "Too Big"
            return
        }
        val textView = findViewById<TextView>(R.id.textView)
        val labelInt = labelString.toInt()
        labelString = labelInt.toString()

        if(currentMode == CalculatorMode.None) {
            savedNum = labelInt
        }

        val df = DecimalFormat("#,###")

        textView.text = df.format(labelInt)
    }

    fun changeMode(mode:CalculatorMode) {
        if(savedNum == 0) {
            return
        }

        currentMode = mode
        lastButtonWasMode = true

    }

    fun didPressNumber(num:Int) {
        val strVal = num.toString()

        if(lastButtonWasMode){
            lastButtonWasMode = false
            labelString = "0"
        }

        labelString = "$labelString$strVal"
        updateText()

    }

}