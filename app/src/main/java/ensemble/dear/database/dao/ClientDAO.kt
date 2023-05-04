package ensemble.dear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ensemble.dear.database.entities.ClientEntity

import ensemble.dear.database.entities.DeliveryEntity;

@Dao
interface ClientDAO {

    @Query("SELECT * FROM client_table ")
    fun getAllClients(): LiveData<List<ClientEntity>>

    @Insert
    fun insert(client: ClientEntity)

    @Update
    fun update(client: ClientEntity)

    @Delete
    fun delete(client: ClientEntity)

}