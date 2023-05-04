package ensemble.dear.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client_table")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val phoneNumber: Int,
    val nameClient: String,
    val photo: String
) { }