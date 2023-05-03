package ensemble.dear


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ensemble.dear.database.ShipmentsDataSource
import ensemble.dear.pendingShipments.Shipment

class ShipmentsDetailViewModel(private val datasource: ShipmentsDataSource) : ViewModel() {

    fun getShipmentForId(id: Int) : Shipment? {
        return datasource.getShipmentByNumber(id)
    }

}

class ShipmentsDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShipmentsDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShipmentsDetailViewModel(
                datasource = ShipmentsDataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}