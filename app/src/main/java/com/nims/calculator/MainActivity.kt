package com.nims.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nims.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    var equation by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = equation,
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.End,
                maxLines = 2,
                lineHeight = 40.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = result,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Keypad
        val buttons = listOf(
            listOf("C", "(", ")", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "DEL", "=")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        label = label,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            when (label) {
                                "C" -> {
                                    equation = ""
                                    result = ""
                                }
                                "DEL" -> {
                                    if (equation.isNotEmpty()) {
                                        equation = equation.dropLast(1)
                                    }
                                }
                                "=" -> {
                                    result = calculateResult(equation)
                                }
                                else -> {
                                    equation += label
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = when (label) {
        "/", "*", "-", "+", "=" -> Color(0xFFFF9800) // Orange for operators
        "C", "DEL", "(", ")" -> Color.Gray
        else -> Color.DarkGray
    }

    val contentColor = Color.White

    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun calculateResult(equation: String): String {
    return try {
        if (equation.isBlank()) return ""
        val res = evaluateSimpleExpression(equation)
        if (res.isNaN()) return "Error"
        if (res == res.toLong().toDouble()) {
            res.toLong().toString()
        } else {
            res.toString()
        }
    } catch (e: Exception) {
        "Error"
    }
}

fun evaluateSimpleExpression(expression: String): Double {
    return try {
        val tokens = tokenize(expression)
        if (tokens.isEmpty()) return 0.0
        evaluateTokens(tokens)
    } catch (e: Exception) {
        Double.NaN
    }
}

fun tokenize(expression: String): List<String> {
    val tokens = mutableListOf<String>()
    var number = ""
    for (char in expression) {
        if (char.isDigit() || char == '.') {
            number += char
        } else if (char == ' ') {
            continue
        } else {
            if (number.isNotEmpty()) {
                tokens.add(number)
                number = ""
            }
            tokens.add(char.toString())
        }
    }
    if (number.isNotEmpty()) tokens.add(number)
    return tokens
}

fun evaluateTokens(tokens: List<String>): Double {
    if (tokens.isEmpty()) return 0.0
    
    // Pass 1: Handle * and /
    val pass1 = mutableListOf<String>()
    var i = 0
    while (i < tokens.size) {
        val token = tokens[i]
        if (token == "*" || token == "/") {
            if (pass1.isEmpty() || i + 1 >= tokens.size) return Double.NaN
            val left = pass1.removeAt(pass1.size - 1).toDoubleOrNull() ?: return Double.NaN
            val right = tokens[i + 1].toDoubleOrNull() ?: return Double.NaN
            val res = if (token == "*") left * right else left / right
            pass1.add(res.toString())
            i += 2
        } else {
            pass1.add(token)
            i++
        }
    }
    
    // Pass 2: Handle + and -
    if (pass1.isEmpty()) return 0.0
    var result = pass1[0].toDoubleOrNull() ?: return Double.NaN
    var j = 1
    while (j < pass1.size) {
        val op = pass1[j]
        if (j + 1 >= pass1.size) return result // Or return NaN? Let's say partial eval.
        val nextVal = pass1[j + 1].toDoubleOrNull() ?: return Double.NaN
        if (op == "+") result += nextVal else if (op == "-") result -= nextVal
        j += 2
    }
    
    return result
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorApp()
    }
}
