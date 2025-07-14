class ShippingUpdate(
    private var previousStatus: String,
    private var newStatus: String,
    private var timestamp: Long
) {
    fun getPreviousStatus(): String {
        return previousStatus
    }

    fun getNewStatus(): String {
        return newStatus
    }

    fun getTimestamp(): Long {
        return timestamp
    }
}