package ensemble.dear.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.entities.DeliveryEntity

class DeliveryRepository(application: Application) {

    private var deliveriesDAO: DeliveryDAO
    private var allDeliveries: LiveData<List<DeliveryEntity>>

    private val database = AppDatabase.getInstance(application)

    init {
        deliveriesDAO = database.deliveriesDAO()
        allDeliveries = deliveriesDAO.getAllDeliveries()
    }

    fun insert(delivery: DeliveryEntity) {
        deliveriesDAO.insert(delivery)

    }

}