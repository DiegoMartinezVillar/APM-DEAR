package ensemble.dear.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "package_table")
data class Package(
    @PrimaryKey
    val packageNumber: Int,
    val address: String,
    val state: String,
    val arrivalDate: LocalDate,
    val idCourier: Int,
    val shipperCompany: String,
    val shipperCompanyPhoto: String,
    val placeLat: Double,
    val placeLong: Double
) { }