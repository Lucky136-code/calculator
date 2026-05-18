package com.nims.multi_viewproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import kotlin.math.pow

class SecondActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        val base = findViewById<EditText>(R.id.base)
        val power = findViewById<EditText>(R.id.power)
        val btnCalc = findViewById<TextView>(R.id.btnCalc)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        val goLcm = findViewById<TextView>(R.id.goLcm)
        val goCalc = findViewById<TextView>(R.id.goCalc)

        btnCalc.setOnClickListener {
            val b = base.text.toString().toDoubleOrNull() ?: 0.0
            val p = power.text.toString().toDoubleOrNull() ?: 0.0
            val res = b.pow(p)
            txtResult.text = "Ans: $res"
        }

        goLcm.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        goCalc.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }
}
