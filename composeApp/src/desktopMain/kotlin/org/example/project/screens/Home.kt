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
import androidx.lifecycle.ViewModel
import org.example.project.ShipmentViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

@Composable
fun Home () {
    var userInput by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val filePath = "data/test.txt"

    val viewModel = remember { ShipmentViewModel() }

    val trackedShipmentIds = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        scope.launch {
            val file = File(filePath)
            if (file.exists()) {
                file.useLines { lines ->
                    for (line in lines) {
                        viewModel.processUpdateString(line)
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
                if (userInput.isNotBlank() && userInput.trim() !in trackedShipmentIds) {
                    trackedShipmentIds.add(userInput.trim())

                }
                userInput = ""
            }) {
                Text("Start Tracking")
            }
        }

        val trackedShipments = viewModel.shipments.filter { it.getId() in trackedShipmentIds }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(trackedShipments) { shipment ->
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
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                            ) {
                            Text(
                                text = "Tracking Shipment: ${shipment.getId()}",
                                fontSize = 24.sp
                            )
                            Text(
                                text = "Status: ${shipment.getStatus()}"
                            )
                            Text(
                                text = "Location: ${shipment.getCurrentLocation()}"
                            )
                            Text(
                                text = "Expected Delivery: ${
                                    if (shipment.getExpectedDeliveryDateTimestamp() != 0L) SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(shipment.getExpectedDeliveryDateTimestamp()))
                                    else "-"
                                }"
                            )
                            Text(
                                text = "Status Updates:"
                            )
                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                shipment.getShipmentUpdates().forEach { update ->
                                    Text(text = "Shipment went from ${update.getPreviousStatus()} to ${update.getNewStatus()} at " +
                                            "${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(update.getTimestamp()))}")
                                }
                            }
                            Text(
                                text = "Notes:"
                            )
                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                shipment.getNotes().forEach { note ->
                                    Text(
                                        text = note
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { trackedShipmentIds.remove(shipment.getId()) },
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