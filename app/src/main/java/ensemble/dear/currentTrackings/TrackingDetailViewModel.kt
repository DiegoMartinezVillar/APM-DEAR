package ensemble.dear.currentTrackings


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.repository.DeliveryRepository
import ensemble.dear.database.repository.PackageRepository

class TrackingDetailViewModel( //private val datasource: TrackingsDataSource
private val repository: DeliveryRepository ) : ViewModel() {

    fun getTrackingForId(id: Int) : DeliveryPackage? {
        //return datasource.getTrackingByNumber(id)

        return repository.getAllById(id)
    }

}

class TrackingDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackingDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrackingDetailViewModel(
                //datasource = TrackingsDataSource.getDataSource(context.resources)
                repository = DeliveryRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}