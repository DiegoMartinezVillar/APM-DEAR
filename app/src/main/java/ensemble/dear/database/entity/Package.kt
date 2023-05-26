package ensemble.dear.database.entity

import androidx.room.ColumnInfo
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
    val idCourier: String,
    val shipperCompany: String,
    val shipperCompanyPhoto: String,
    val additionalInstructions: String,
    val placeLat: Double,
    val placeLong: Double,
    @ColumnInfo(name = "signature", typeAffinity = ColumnInfo.BLOB)
    val signature: ByteArray?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Package

        if (packageNumber != other.packageNumber) return false

        return true
    }

    override fun hashCode(): Int {
        return packageNumber
    }
}