package ensemble.dear.currentTrackings

import java.time.LocalDate

const val PRE_ADMISSION_STATE : String = "pre_admission"
const val ON_THE_WAY_STATE : String = "on_the_way"
const val IN_DELIVERY_STATE : String = "in_delivery"
const val DELIVERED_STATE : String = "delivered"


class TrackingsProvider {

    companion object {
        val trackingsList = listOf<Tracking>(
            Tracking(
                packageNumber = 123456789,
                packageContent = "Alarm Clock",
                daysUntilArrival = 2,
                shipperCompany = "Aliexpress",
                estimatedArrivalDate = LocalDate.of(2023, 4, 20),
                //estimatedTimeOfArrival
                currentState = IN_DELIVERY_STATE,
                assignedCourier = "1",
                deliveryAddress = "Address of delivery"
            ),
            Tracking(
                packageNumber = 121212121,
                packageContent = "Bike shorts",
                daysUntilArrival = 3,
                shipperCompany = "Alibaba",
                estimatedArrivalDate = LocalDate.of(2023, 4, 13),
                //estimatedTimeOfArrival
                currentState = PRE_ADMISSION_STATE,
                assignedCourier = "3",
                deliveryAddress = "Address of delivery 2"
            ),
            Tracking(
                packageNumber = 666666666,
                packageContent = "Floral cushion",
                daysUntilArrival = 3,
                shipperCompany = "Alibaba",
                estimatedArrivalDate = LocalDate.of(2023, 4, 10),
                //estimatedTimeOfArrival
                currentState = ON_THE_WAY_STATE,
                assignedCourier = "2",
                deliveryAddress = "Address of delivery 3"
            ),
        )
    }
}