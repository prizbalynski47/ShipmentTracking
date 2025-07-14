package org.example.project.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.Destinations
import java.io.File
import kotlin.concurrent.thread

@Composable
fun Home () {
    var userInput by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val filePath = "data/test.txt"

    LaunchedEffect(Unit) {
        scope.launch {
            val file = File(filePath)
            if (file.exists()) {
                file.useLines { lines ->
                    for (line in lines) {
                        println(line)
                        delay(1000)
                    }
                }
            }
        }
    }

    Row {
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            modifier = Modifier.weight(1f)
        )
        Button(onClick = {

        }) {
            Text("Start Tracking")
        }
    }
}