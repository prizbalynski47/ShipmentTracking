package org.example.project

import ExpectedDeliveryOtherInfoStrategy
import NewLocationOtherInfoStrategy
import NoOtherInfoStrategy
import NoteOtherInfoStrategy
import Shipment
import Update
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class ShipmentViewModel {

    // A list of shipments that the UI will observe
    val shipments: SnapshotStateList<Shipment> = mutableStateListOf()

    fun processUpdateString(updateString: String) {
        val parts = updateString.split(",")
        if (parts.size < 3 || parts.size > 4) return  // invalid update

        val updateType = parts[0]
        val shipmentId = parts[1]
        val timestamp = parts[2].toLong()
        val otherInfo = if (parts.size == 4) parts[3] else ""

        if (updateType == "created") {
            // Create a new shipment
            if (shipments.none { it.getId() == shipmentId }) {
                shipments.add(Shipment(shipmentId))
            }
        } else {
            // Apply update to an existing shipment
            val shipment = shipments.find { it.getId() == shipmentId }
            if (shipment != null) {
                val update = buildUpdate(updateType, timestamp, otherInfo)
                shipment.update(update)
            } else {
                // Optionally create shipment if missing
                val newShipment = Shipment(shipmentId)
                shipments.add(newShipment)
                val update = buildUpdate(updateType, timestamp, otherInfo)
                newShipment.update(update)
            }
        }
    }

    private fun buildUpdate(updateType: String, timestamp: Long, otherInfo: String): Update {
        return when (updateType) {
            "shipped" -> Update(updateType, timestamp, ExpectedDeliveryOtherInfoStrategy(),otherInfo)
            "location" -> Update(updateType, timestamp, NewLocationOtherInfoStrategy(),otherInfo)
            "delayed" -> Update(updateType, timestamp, ExpectedDeliveryOtherInfoStrategy(),otherInfo)
            "noteadded" -> Update(updateType, timestamp, NoteOtherInfoStrategy(),otherInfo)
            "canceled" -> Update(updateType, timestamp, NoOtherInfoStrategy())
            "lost" -> Update(updateType, timestamp, NoOtherInfoStrategy())
            "delivered" -> Update(updateType, timestamp, NoOtherInfoStrategy())
            else -> Update(updateType, timestamp, NoteOtherInfoStrategy(),"unknown update type")
        }
    }
}