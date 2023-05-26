package ensemble.dear.pendingShipments


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.entity.Package
import ensemble.dear.database.repository.PackageRepository

class ShipmentsDetailViewModel(private val repository: PackageRepository) : ViewModel() {

    fun getShipmentForId(id: Int) : Package? {
        return repository.getPackageByNumber(id)
    }

}

class ShipmentsDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShipmentsDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShipmentsDetailViewModel(
                repository =  PackageRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}