package ensemble.dear.database.entity

import androidx.room.*
import ensemble.dear.database.converter.LocalDateConverter
import java.time.LocalDate

data class DeliveryPackage(
    val idDelivery: Int,
    val packageNumber: Int,
    val address: String,
    val state: String,
    val arrivalDate: LocalDate,
    val shipperCompany: String,
    //val shipperCompanyPhoto: String,
    val additionalInstructions: String,
    val packageAlias: String
) { }