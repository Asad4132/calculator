package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.util.Log
import java.lang.String.valueOf
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private lateinit var screen : TextView
    private var display : String? = ""
    private var result : String? = ""
    private var currentOperator : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screen = findViewById(R.id.textView)
        screen.setText(display)
    }

    fun onClickNumber (view : View){
        if(result != "")
        {
            clear()
            screen.setText(display)
        }
        var button = view as Button
        display += view.text
        screen.setText(display)
    }

    fun onClickCancel(v: View) {
        clear()
        screen.setText(display)
    }

    fun onClickOperator(v: View) {
        if (display === "") return

        val b = v as Button

        if (result !== "") {
            val _display = result
            clear()
            display = _display
        }

        if (currentOperator !== "") {
            if (isOperator(display!!.get(display!!.length - 1))) {
                display = display?.replace(display!!.get(display!!.length - 1), b.text[0])
                screen.setText(display)
                return
            } else {
                getResult()
                display = result
                result = ""
            }
            currentOperator = b.text.toString()
        }
        display += b.text
        currentOperator = b.text.toString()
        screen.setText(display)
    }

    fun onClickEqual(v: View) {
        if (display === "") return
        if (!getResult()) return
        screen.setText(display + "\n" + valueOf(result))
    }

    private fun clear() {
        display = ""
        currentOperator = ""
        result = ""
    }

    private fun isOperator(op: Char): Boolean {
        when (op) {
            '+', '-', '*', '/' -> return true
            else -> return false
        }
    }

    fun getResult(): Boolean {
        if (currentOperator === "") return false
        val operation = display!!.split(currentOperator!!)
        if (operation.size < 2) return false
        result = valueOf(operate(operation[0], operation[1], currentOperator!!))
        return true
    }

    private fun operate(a: String, b: String, op: String): Double {
        when (op) {
            "+" -> return java.lang.Double.valueOf(a) + java.lang.Double.valueOf(b)
            "-" -> return java.lang.Double.valueOf(a) - java.lang.Double.valueOf(b)
            "*" -> return java.lang.Double.valueOf(a) * java.lang.Double.valueOf(b)
            "/" -> {
                try {
                    return java.lang.Double.valueOf(a) / java.lang.Double.valueOf(b)
                } catch (e: Exception) {
                    Log.d("Calc", e.message)
                }

                return -1.0
            }
            else -> return -1.0
        }
    }

}
