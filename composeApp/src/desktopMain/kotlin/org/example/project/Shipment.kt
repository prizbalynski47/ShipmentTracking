import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Shipment(
    private val id: String,
) {
    private var _status by mutableStateOf("created")
    private var _expectedDeliveryDateTimestamp by mutableStateOf(0L)
    private var _currentLocation by mutableStateOf("unknown")

    private val shipmentUpdates = mutableStateListOf<ShippingUpdate>()
    private val notes = mutableStateListOf<String>()

    fun storeUpdate(update: ShippingUpdate) {
        shipmentUpdates += update
    }

    fun update(update: Update) {
        update.applyUpdate(this)
    }

    fun setCurrentLocation(location: String) {
        _currentLocation = location
    }

    fun setExpectedDeliveryDateTimestamp(timestamp: Long) {
        _expectedDeliveryDateTimestamp = timestamp
    }

    fun setStatus(newStatus: String) {
        _status = newStatus
    }

    fun getStatus(): String = _status

    fun getId(): String = id

    fun getNotes(): List<String> = notes

    fun addNote(note: String) {
        notes += note
    }

    fun getExpectedDeliveryDateTimestamp(): Long = _expectedDeliveryDateTimestamp

    fun getShipmentUpdates(): List<ShippingUpdate> = shipmentUpdates

    fun getCurrentLocation(): String = _currentLocation
}