package ensemble.dear.currentTrackings


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.repository.DeliveryRepository

class TrackingDetailViewModel( private val repository: DeliveryRepository ) : ViewModel() {

    fun getTrackingForId(id: Int) : DeliveryPackage? {
        return repository.getPackageById(id)
    }

}

class TrackingDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackingDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrackingDetailViewModel(
                repository = DeliveryRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}