package ensemble.dear.database.dao

import androidx.room.*
import ensemble.dear.database.entity.AuthorizedCourier

@Dao
interface AuthorizedCourierDAO {

    @Insert
    fun insertAuthorizedCourier(authorizedCouriers: AuthorizedCourier)

    @Query("Select * from authorizedCourier")
    fun gelAllAuthorizedCouriers(): List<AuthorizedCourier>

    @Query("SELECT * FROM authorizedCourier WHERE email = :idCourier")
    fun getAuthorizedCourierById(idCourier: String): AuthorizedCourier

    @Update
    fun updateAuthorizedCourier(authorizedCouriers: AuthorizedCourier)

    @Delete
    fun deleteAuthorizedCourier(authorizedCouriers: AuthorizedCourier)

}