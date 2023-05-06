package ensemble.dear.database.repository

import android.content.Context
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.entity.Delivery
import ensemble.dear.database.entity.DeliveryPackage

class DeliveryRepository(context: Context) {

    //private val database = AppDatabase.getInstance(application)

    var deliveriesDAO: DeliveryDAO = AppDatabase.getInstance(context)?.deliveryDAO()!!


    fun getAllById(idPackage: Int): DeliveryPackage {
        return deliveriesDAO.getPackagesById(idPackage)
    }

    fun getAllUser(idUser: Int): List<DeliveryPackage> {
        return deliveriesDAO.getPackagesUser()
    }

    fun insert(delivery: Delivery) {
        deliveriesDAO.insert(delivery)
    }

    fun delete(delivery: Delivery) {
        deliveriesDAO.delete(delivery)
    }

    fun getAll(): List<Delivery> {
        return deliveriesDAO.getAllDeliveries()
    }


}