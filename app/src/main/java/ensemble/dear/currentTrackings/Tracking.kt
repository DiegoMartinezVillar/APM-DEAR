package ensemble.dear.currentTrackings

import java.time.LocalDate

data class Tracking(
    val packageNumber: Int,
    val packageContent: String,
    val daysUntilArrival: Int,
    val shipperCompany: String,
    val estimatedArrivalDate: LocalDate,
    val currentState: String,
    val assignedCourier: String,
    val deliveryAddress: String
) { }