package org.example.project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.Destinations
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import java.io.File
import kotlin.concurrent.thread

@Composable
fun Home () {
    var userInput by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val filePath = "data/test.txt"

    val displayedLines = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        scope.launch {
            val file = File(filePath)
            if (file.exists()) {
                file.useLines { lines ->
                    for (line in lines) {
                        println(line)
//                        displayedLines += line
                        delay(1000)
                    }
                }
            }
        }
    }

    Column {
        Row {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (userInput.isNotBlank()) {
                    displayedLines.add(userInput.trim())
                    userInput = "" // clear input
                }
            }) {
                Text("Start Tracking")
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(displayedLines) { line ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = line
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { displayedLines.remove(line) },
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("X")
                        }
                    }
                }
            }
        }
    }
}