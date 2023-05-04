package ensemble.dear.database.dao

import androidx.room.*
import ensemble.dear.database.entities.AuthorizedCourier

@Dao
interface AuthorizedCourierDAO {

    @Insert
    fun insertAuthorizedCourier(authorizedCouriers: AuthorizedCourier)

    @Query("Select * from authorizedCourier")
    fun gelAllAuthorizedCouriers(): List<AuthorizedCourier>

    @Update
    fun updateAuthorizedCourier(authorizedCouriers: AuthorizedCourier)

    @Delete
    fun deleteAuthorizedCourier(authorizedCouriers: AuthorizedCourier)

}