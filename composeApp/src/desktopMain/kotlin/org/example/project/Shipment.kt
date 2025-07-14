class Shipment(
    private val id: String,
) {
    private var status: String = "created"
    private val notes = mutableListOf<String>()
    private var expectedDeliveryDateTimestamp = 0L
    private var currentLocation = "unknown"
    private val shipmentUpdates = mutableListOf<ShippingUpdate>()

    fun storeUpdate(update: ShippingUpdate) {
        shipmentUpdates += update
    }

    fun update(update: Update) {
        update.applyUpdate(this)
    }

    fun setCurrentLocation(location: String) {
        currentLocation = location
    }

    fun setExpectedDeliveryDateTimestamp(timestamp: Long) {
        expectedDeliveryDateTimestamp = timestamp
    }

    fun setStatus(newStatus: String) {
        status = newStatus
    }

    fun getStatus(): String {
        return status
    }

    fun getId(): String {
        return id
    }

    fun getNotes(): MutableList<String> {
        return notes
    }

    fun addNote(note: String) {
        notes += note
    }

    fun getExpectedDeliveryDateTimestamp(): Long {
        return expectedDeliveryDateTimestamp
    }

    fun getShipmentUpdates(): MutableList<ShippingUpdate> {
        return shipmentUpdates
    }

    fun getCurrentLocation(): String {
        return currentLocation
    }
}