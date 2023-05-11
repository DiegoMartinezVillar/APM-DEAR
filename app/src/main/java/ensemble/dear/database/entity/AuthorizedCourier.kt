package ensemble.dear.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authorizedCourier")
data class AuthorizedCourier(
    @PrimaryKey var email: String
)