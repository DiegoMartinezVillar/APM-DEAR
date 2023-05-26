package ensemble.dear.place

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.maps.model.LatLng
import ensemble.dear.database.repository.PackageRepository
import ensemble.dear.database.entity.Package

/**
 * Reads the list of packages from the repository
 */
class PlacesReader(private val context: Context) {

    /**
     * Gets the list of all packages
     * and returns a list of places to be displayed on the map as markers
     */
    fun read(): List<Place> {
        val allPackages = PackageRepository(context).getAll()
        return allPackages.map {
            Place(it.packageNumber.toString(), LatLng(it.placeLat, it.placeLong), it.address)
        }
    }

    /**
     * Get packages for today for the logged courier, or all on failure
     * and returns a list of places to be displayed on the map as markers
     */
    fun readToday(): List<Place> {
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(context)
        val allPackages = if(acct != null) {
            PackageRepository(context).getAllCourierPackagesForToday(acct.email.toString())
        } else {
            PackageRepository(context).getAll()
        }

        return allPackages.map {
            Place(it.packageNumber.toString(), LatLng(it.placeLat, it.placeLong), it.address)
        }
    }
}