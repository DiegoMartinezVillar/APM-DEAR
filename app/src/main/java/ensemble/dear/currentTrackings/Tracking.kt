package ensemble.dear.currentTrackings

data class Tracking(
    val packageContent: String,
    val packageNumber: Int,
    val daysUntilArrival: Int,
    val shipperCompany: String
) {


}