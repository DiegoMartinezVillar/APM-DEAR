package ensemble.dear.database.repository

import android.content.Context
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.entity.Delivery
import ensemble.dear.database.entity.DeliveryPackage

class DeliveryRepository(context: Context) {

    var deliveriesDAO: DeliveryDAO = AppDatabase.getInstance(context)?.deliveryDAO()!!

    fun getPackageById(idPackage: Int): DeliveryPackage {
        return deliveriesDAO.getPackageById(idPackage)
    }

    fun getAllUser(idUser: String): List<DeliveryPackage> {
        return deliveriesDAO.getPackagesUser(idUser)
    }

    fun getUserPastPackages(idUser: String): List<DeliveryPackage> {
        return deliveriesDAO.getPastPackages(idUser)
    }

    fun insert(delivery: Delivery) {
        deliveriesDAO.insert(delivery)
    }

    fun delete(idDelivery: Int) {
        deliveriesDAO.delete(idDelivery)
    }

    fun getAll(): List<Delivery> {
        return deliveriesDAO.getAllDeliveries()
    }


}