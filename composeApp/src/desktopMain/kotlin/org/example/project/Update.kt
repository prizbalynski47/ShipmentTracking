abstract class Update(
    private val type: String,
    private val timestamp: Long,
    private val otherInfo: String = ""
) {
    abstract val otherInfoStrategy: OtherInfoStrategy

    fun applyUpdate(shipment: Shipment) {
        otherInfoStrategy.applyOtherInfo(shipment, otherInfo)
        shipment.storeUpdate(ShippingUpdate(shipment.getStatus(), type, timestamp))
        shipment.setStatus(type)
    }
}