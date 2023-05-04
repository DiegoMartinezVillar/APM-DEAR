package ensemble.dear.database.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entities.DeliveryEntity
import ensemble.dear.database.entities.PackageEntity

class DeliveryRepository(context: Context) {


    private val database = AppDatabase.getInstance(context)

    var deliveriesDAO: DeliveryDAO = AppDatabase.getInstance(context)?.deliveriesDAO()!!


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