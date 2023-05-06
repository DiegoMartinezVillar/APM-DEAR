package ensemble.dear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ensemble.dear.database.entity.Client

@Dao
interface ClientDAO {

    @Query("SELECT * FROM client_table ")
    fun getAllClients(): LiveData<List<Client>>

    @Insert
    fun insert(client: Client)

    @Update
    fun update(client: Client)

    @Delete
    fun delete(client: Client)

}