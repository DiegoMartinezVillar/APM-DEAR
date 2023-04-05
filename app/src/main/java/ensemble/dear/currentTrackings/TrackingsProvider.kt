package ensemble.dear.currentTrackings

class TrackingsProvider {

    companion object {
        val trackingsList = listOf<Tracking>(
            Tracking(
                "Alarm Clock",
                packageNumber = 12345678,
                daysUntilArrival = 2,
                shipperCompany = "Aliexpress"
            ),
            Tracking(
                "Bike",
                packageNumber = 123456789,
                daysUntilArrival = 3,
                shipperCompany = "Aliexpress"
            ),
        )
    }
}