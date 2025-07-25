class Update(
    private val type: String,
    private val timestamp: Long,
    private val otherInfoStrategy: OtherInfoStrategy,
    private val otherInfo: String = ""
) {

    fun applyUpdate(shipment: Shipment) {
        otherInfoStrategy.applyOtherInfo(shipment, otherInfo)
        shipment.storeUpdate(ShippingUpdate(shipment.getStatus(), type, timestamp))
        shipment.setStatus(type)
    }
}