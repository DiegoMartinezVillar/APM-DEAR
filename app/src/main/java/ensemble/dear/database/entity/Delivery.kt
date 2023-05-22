package ensemble.dear.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_table",
foreignKeys = [
    ForeignKey(
        entity = Package::class,
        childColumns = ["idPackage"],
        parentColumns = ["packageNumber"])
    ])
data class Delivery(
    @PrimaryKey(autoGenerate = true)
    val idDelivery: Int,
    val idPackage: Int,
    val packageAlias: String,
    val signature: String,
    //val alarm: String
    val idClient: String
) { }