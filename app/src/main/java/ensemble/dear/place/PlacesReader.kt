package ensemble.dear.place

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import ensemble.dear.database.repository.PackageRepository

/**
 * Reads the list of packages from the repository
 */
class PlacesReader(private val context: Context) {

    /**
     * Gets the list of packages
     * and returns a list of places to be displayed on the map as markers
     */
    fun read(): List<Place> {
        val allPackages = PackageRepository(context).getAll()
        return allPackages.map {
            Place(it.packageNumber.toString(), LatLng(it.placeLat, it.placeLong), it.address)
        }
    }
}