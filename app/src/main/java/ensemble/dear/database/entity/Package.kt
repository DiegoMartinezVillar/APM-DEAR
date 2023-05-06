package ensemble.dear.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "package_table")
data class Package(
    @PrimaryKey
    val packageNumber: Int,
    val packageContent: String,
    val address: String,
    val state: String,
    //@Ignore
    //val arrivalDate: LocalDate,
    val arrivalDate: String,
    val idCourier: Int,
    val shipperCompany: String,
    val shipperCompanyPhoto: String
) { }