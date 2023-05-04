package ensemble.dear.database.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "package_table")
data class PackageEntity(
    @PrimaryKey
    val packageNumber: Int,
    val address: String,
    val state: String,
    //@Ignore
    //val arrivalDate: LocalDate,
    val idCourier: Int
) { }