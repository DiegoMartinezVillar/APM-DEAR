package ensemble.dear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*

import ensemble.dear.database.entities.DeliveryEntity;

@Dao
interface DeliveryDAO {

    @Query("SELECT * FROM delivery_table ")
    fun getAllDeliveries(): LiveData<List<DeliveryEntity>>

    @Insert
    fun insert(delivery: DeliveryEntity)

    @Update
    fun update(delivery: DeliveryEntity)

    @Delete
    fun delete(delivery: DeliveryEntity)

}