class NoteOtherInfoStrategy(): OtherInfoStrategy {
    override fun applyOtherInfo(shipment: Shipment, otherInfo: String) {
        shipment.addNote(otherInfo)
    }
}