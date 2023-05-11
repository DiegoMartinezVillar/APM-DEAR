package ensemble.dear.pendingShipments

import ensemble.dear.database.IN_DELIVERY_STATE
import ensemble.dear.database.ON_THE_WAY_STATE
import ensemble.dear.database.PRE_ADMISSION_STATE
import java.time.LocalDate

class ShipmentsProvider {

    companion object {
        val shipmentsList = listOf<Shipment>(
            Shipment(
                packageNumber = 123456789,
                packageContent = "Alarm Clock",
                daysUntilArrival = 2,
                shipperCompany = "Aliexpress",
                estimatedArrivalDate = LocalDate.of(2023, 4, 20),
                //estimatedTimeOfArrival
                currentState = IN_DELIVERY_STATE,
                assignedCourier = "1",
                deliveryAddress = "436 Constitution Way " +
                        "San Francisco, California",
                additionalInstructions = "The house is next to John's bakery"
                //receiver
            ),
            Shipment(
                packageNumber = 121212121,
                packageContent = "Bike shorts",
                daysUntilArrival = 3,
                shipperCompany = "Alibaba",
                estimatedArrivalDate = LocalDate.of(2023, 4, 13),
                //estimatedTimeOfArrival
                currentState = PRE_ADMISSION_STATE,
                assignedCourier = "3",
                deliveryAddress = "29 Idlewood Dr " +
                        "San Francisco, California",
                additionalInstructions = "Send a message around ten minutes before arriving"
            ),
            Shipment(
                packageNumber = 666666666,
                packageContent = "Floral cushion",
                daysUntilArrival = 3,
                shipperCompany = "Alibaba",
                estimatedArrivalDate = LocalDate.of(2023, 4, 10),
                //estimatedTimeOfArrival
                currentState = ON_THE_WAY_STATE,
                assignedCourier = "2",
                deliveryAddress = "114 Camaritas Ave " +
                        "San Francisco, California",
                additionalInstructions = "Leave at front door"
            ),
        )
    }
}