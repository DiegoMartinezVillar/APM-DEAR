package ensemble.dear.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_table")
data class DeliveryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idCourier: Int,
    val additionalInstructions: String,
    val packageAlias: String,
    val signature: String
) { }