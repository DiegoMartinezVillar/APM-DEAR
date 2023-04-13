package ensemble.dear.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ensemble.dear.currentTrackings.Tracking
import ensemble.dear.currentTrackings.TrackingsProvider
import ensemble.dear.pendingShipments.Shipment
import ensemble.dear.pendingShipments.ShipmentsProvider

class ShipmentsDataSource(resources: Resources) {
    private val shipmentsLiveData = MutableLiveData(ShipmentsProvider.shipmentsList)

    // adds tracking to liveData and posts value
    fun addShipment(shipment: Shipment) {
        val currentList = shipmentsLiveData.value
        if (currentList == null) {
            shipmentsLiveData.postValue(listOf(shipment))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, shipment)
            shipmentsLiveData.postValue(updatedList)
        }
    }


    // returns shipment given an ID
    fun getShipmentByNumber(id: Int): Shipment? {
        shipmentsLiveData.value?.let { trackings ->
            return trackings.firstOrNull{ it.packageNumber == id}
        }
        return null
    }

    fun getShipmentssList(): LiveData<List<Shipment>> {
        return shipmentsLiveData
    }


    companion object {
        private var INSTANCE: ShipmentsDataSource? = null

        fun getDataSource(resources: Resources): ShipmentsDataSource {
            return synchronized(ShipmentsDataSource::class) {
                val newInstance = INSTANCE ?: ShipmentsDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}