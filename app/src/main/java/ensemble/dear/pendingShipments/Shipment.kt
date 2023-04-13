package ensemble.dear.pendingShipments

import java.time.LocalDate

data class Shipment (
    val packageNumber: Int,
    val packageContent: String,
    val daysUntilArrival: Int,
    val shipperCompany: String,
    val estimatedArrivalDate: LocalDate,
    val currentState: String,
    val assignedCourier: String,
    val deliveryAddress: String,
    val additionalInstructions: String
) { }