package ensemble.dear.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authorizedCourier")
data class AuthorizedCourier(
    @PrimaryKey var email: String
)