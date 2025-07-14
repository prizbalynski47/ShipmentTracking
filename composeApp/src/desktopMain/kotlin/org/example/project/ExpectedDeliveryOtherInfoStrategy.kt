class ExpectedDeliveryOtherInfoStrategy(): OtherInfoStrategy {
    override fun applyOtherInfo(shipment: Shipment, otherInfo: String) {
        shipment.setExpectedDeliveryDateTimestamp(otherInfo.toLong())
    }
}