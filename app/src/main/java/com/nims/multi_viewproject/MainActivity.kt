package com.nims.multi_viewproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val num1 = findViewById<EditText>(R.id.num1)
        val num2 = findViewById<EditText>(R.id.num2)
        val btnCalc = findViewById<TextView>(R.id.btnCalc)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        val goPower = findViewById<TextView>(R.id.goPower)
        val goCalc = findViewById<TextView>(R.id.goCalc)

        btnCalc.setOnClickListener {
            val a = num1.text.toString().toLongOrNull() ?: 0L
            val b = num2.text.toString().toLongOrNull() ?: 0L
            if (a > 0 && b > 0) {
                fun gcd(x: Long, y: Long): Long = if (y == 0L) x else gcd(y, x % y)
                val res = (a * b) / gcd(a, b)
                txtResult.text = "Result is: $res"
            } else {
                txtResult.text = "wrong input"
            }
        }

        goPower.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        goCalc.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }
}
