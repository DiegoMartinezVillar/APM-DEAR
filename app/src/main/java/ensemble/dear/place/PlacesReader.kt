package ensemble.dear.place

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import ensemble.dear.R
import java.io.InputStream
import java.io.InputStreamReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ensemble.dear.database.repository.PackageRepository

/**
 * Reads a list of place JSON objects from the file places.json
 */
class PlacesReader(private val context: Context) {

    // GSON object responsible for converting from JSON to a Place object
    private val gson = Gson()

    // InputStream representing places.json
    private val inputStream: InputStream
        get() = context.resources.openRawResource(R.raw.places)

    /**
     * Reads the list of place JSON objects in the file places.json
     * and returns a list of Place objects
     */
    fun read(): List<Place> {
        val allPackages = PackageRepository(context).getAll()
        return allPackages.map {
            Place(it.packageNumber.toString(), LatLng(it.placeLat, it.placeLong), it.address)
        }
    }
}