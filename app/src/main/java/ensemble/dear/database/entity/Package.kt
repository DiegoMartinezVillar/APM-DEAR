package ensemble.dear.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ensemble.dear.database.converter.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "package_table")
data class Package(
    @PrimaryKey
    val packageNumber: Int,
    val packageContent: String,
    val address: String,
    val state: String,
    val arrivalDate: LocalDate,
    val idCourier: Int,
    val shipperCompany: String,
    val shipperCompanyPhoto: String
) { }