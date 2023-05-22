package ensemble.dear.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.android.gms.maps.model.LatLng
import ensemble.dear.database.converter.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "package_table")
data class Package(
    @PrimaryKey
    val packageNumber: Int,
    val address: String,
    val state: String,
    val arrivalDate: LocalDate,
    val idCourier: String,
    val shipperCompany: String,
    val shipperCompanyPhoto: String,
    val additionalInstructions: String,
    val placeLat: Double,
    val placeLong: Double
) { }