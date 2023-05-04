package ensemble.dear.database

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ensemble.dear.currentTrackings.Tracking
import ensemble.dear.currentTrackings.TrackingsProvider

class TrackingsDataSource(resources: Resources) {
    private val trackingsLiveData = MutableLiveData(TrackingsProvider.trackingsList)

    // adds tracking to liveData and posts value
    fun addTracking(tracking: Tracking) {
        val currentList = trackingsLiveData.value
        if (currentList == null) {
            trackingsLiveData.postValue(listOf(tracking))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, tracking)
            trackingsLiveData.postValue(updatedList)
        }
    }

    // Removes tracking from liveData and posts value
    fun removeTracking(tracking: Tracking) {
        val currentList = trackingsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(tracking)
            trackingsLiveData.postValue(updatedList)





        }
    }

    /* Returns Tracking given an ID. */
    fun getTrackingByNumber(id: Int): Tracking? {
        trackingsLiveData.value?.let { trackings ->
            return trackings.firstOrNull{ it.packageNumber == id}
        }
        return null
    }

    fun getTrackingsList(): LiveData<List<Tracking>> {
        return trackingsLiveData
    }


    companion object {
        private var INSTANCE: TrackingsDataSource? = null

        fun getDataSource(resources: Resources): TrackingsDataSource {
            return synchronized(TrackingsDataSource::class) {
                val newInstance = INSTANCE ?: TrackingsDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}