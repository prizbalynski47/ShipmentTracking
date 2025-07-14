class NewLocationOtherInfoStrategy(): OtherInfoStrategy {
    override fun applyOtherInfo(shipment: Shipment, otherInfo: String) {
        shipment.setCurrentLocation(otherInfo)
    }
}