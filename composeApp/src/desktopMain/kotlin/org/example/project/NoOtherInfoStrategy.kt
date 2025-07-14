class NoOtherInfoStrategy(): OtherInfoStrategy {
    override fun applyOtherInfo(shipment: Shipment, otherInfo: String) {
        // noop
    }
}