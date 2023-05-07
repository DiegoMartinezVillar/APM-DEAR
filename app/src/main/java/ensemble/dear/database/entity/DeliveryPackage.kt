package ensemble.dear.database.entity

import androidx.room.*

data class DeliveryPackage(
    val idDelivery: Int,
    val packageNumber: Int,
    val address: String,
    val state: String,
    val arrivalDate: String,
    val packageContent: String,
    val shipperCompany: String,
    //val shipperCompanyPhoto: String,
    val additionalInstructions: String,
    val packageAlias: String
) { }