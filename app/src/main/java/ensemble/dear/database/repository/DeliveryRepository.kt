package ensemble.dear.database.repository

import android.content.Context
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.entities.DeliveryEntity

class DeliveryRepository(context: Context) {

    //private val database = AppDatabase.getInstance(application)

    var deliveriesDAO: DeliveryDAO = AppDatabase.getInstance(context)?.deliveryDAO()!!


    fun insert(delivery: DeliveryEntity) {
        deliveriesDAO.insert(delivery)
    }

    fun delete(delivery: DeliveryEntity) {
        deliveriesDAO.delete(delivery)
    }

    fun getAll(): List<DeliveryEntity> {
        return deliveriesDAO.getAllDeliveries()
    }


}