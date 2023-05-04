package ensemble.dear


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ensemble.dear.currentTrackings.Tracking
import ensemble.dear.database.TrackingsDataSource

class TrackingDetailViewModel(private val datasource: TrackingsDataSource) : ViewModel() {

    fun getTrackingForId(id: Int) : Tracking? {
        return datasource.getTrackingByNumber(id)
    }

    fun removeTracking(tracking: Tracking) {
        datasource.removeTracking(tracking)
    }
}

class TrackingDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackingDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrackingDetailViewModel(
                datasource = TrackingsDataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}