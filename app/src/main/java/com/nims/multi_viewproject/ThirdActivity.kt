package com.nims.multi_viewproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class ThirdActivity : Activity() {
    private var expr = ""
    private var res = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        val txtInput = findViewById<TextView>(R.id.txtInput)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        val goLcm = findViewById<TextView>(R.id.goLcm)
        val goPower = findViewById<TextView>(R.id.goPower)

        val keys = mapOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2", R.id.btn3 to "3",
            R.id.btn4 to "4", R.id.btn5 to "5", R.id.btn6 to "6", R.id.btn7 to "7",
            R.id.btn8 to "8", R.id.btn9 to "9", R.id.btnPlus to "+", R.id.btnMin to "-",
            R.id.btnMul to "*", R.id.btnDiv to "/"
        )

        fun updateUI() {
            txtInput.text = "Input: $expr"
            txtResult.text = "Result: $res"
        }

        keys.forEach { (id, value) ->
            findViewById<TextView>(id).setOnClickListener {
                expr += value
                updateUI()
            }
        }

        findViewById<TextView>(R.id.btnClear).setOnClickListener {
            expr = ""
            res = ""
            updateUI()
        }

        findViewById<TextView>(R.id.btnEq).setOnClickListener {
            try {
                val op = if (expr.contains("+")) "+" else if (expr.contains("-")) "-" else if (expr.contains("*")) "*" else if (expr.contains("/")) "/" else ""
                if (op != "") {
                    val parts = expr.split(op)
                    val v1 = parts[0].toDouble()
                    val v2 = parts[1].toDouble()
                    res = when (op) {
                        "+" -> (v1 + v2).toString()
                        "-" -> (v1 - v2).toString()
                        "*" -> (v1 * v2).toString()
                        "/" -> (v1 / v2).toString()
                        else -> ""
                    }
                }
            } catch (e: Exception) {
                res = "error"
            }
            updateUI()
        }

        goLcm.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        goPower.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}
