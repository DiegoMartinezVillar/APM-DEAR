package ensemble.dear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ensemble.dear.database.entities.DeliveryEntity;

@Dao
interface DeliveryDAO {

    @Query("SELECT * FROM delivery_table ")
    fun getAllDeliveries(): LiveData<List<DeliveryEntity>>
    //suspend

    @Insert
    fun insert(delivery: DeliveryEntity)


}