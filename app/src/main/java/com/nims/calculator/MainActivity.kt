package com.nims.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var input by remember { mutableStateOf("") }
            var output by remember { mutableStateOf("") }

            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "My Calculator", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(20.dp))
                
                Text(text = "Input: " + input, fontSize = 18.sp)
                Text(text = "Output: " + output, fontSize = 18.sp)
                
                Spacer(modifier = Modifier.height(30.dp))

                // Simple rows of text
                val rows = listOf(
                    listOf("7", "8", "9", "/"),
                    listOf("4", "5", "6", "*"),
                    listOf("1", "2", "3", "-"),
                    listOf("0", "C", "=", "+")
                )

                for (row in rows) {
                    Row {
                        for (item in row) {
                            Text(
                                text = item,
                                modifier = Modifier
                                    .padding(25.dp)
                                    .clickable {
                                        if (item == "C") {
                                            input = ""
                                            output = ""
                                        } else if (item == "=") {
                                            output = simpleSolve(input)
                                        } else {
                                            input = input + item
                                        }
                                    },
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

fun simpleSolve(input: String): String {
    return try {
        // Just a very basic solver for a simple page
        if (input.contains("+")) {
            val parts = input.split("+")
            (parts[0].toDouble() + parts[1].toDouble()).toString()
        } else if (input.contains("-")) {
            val parts = input.split("-")
            (parts[0].toDouble() - parts[1].toDouble()).toString()
        } else if (input.contains("*")) {
            val parts = input.split("*")
            (parts[0].toDouble() * parts[1].toDouble()).toString()
        } else if (input.contains("/")) {
            val parts = input.split("/")
            (parts[0].toDouble() / parts[1].toDouble()).toString()
        } else {
            input
        }
    } catch (e: Exception) {
        "Error"
    }
}
