package ensemble.dear.database.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entities.PackageEntity

class PackageRepository(application: Application) {

    private var packageDAO: PackageDAO
    private var allPackages: LiveData<List<PackageEntity>>

    private val database = AppDatabase.getInstance(application)


    init {
        packageDAO = database.packageDAO()
        allPackages = packageDAO.getAllPackages()
    }

    fun insert(packageEnt: PackageEntity) {
        packageDAO.insert(packageEnt)
    }

    fun getAll(): LiveData<List<PackageEntity>> {
        return packageDAO.getAllPackages()
    }

    companion object {
        private var instance: PackageRepository? = null

        @Synchronized
        fun getInstance(ctx: Context): PackageRepository {
            if (instance == null) {
                instance = PackageRepository(ctx as Application)

            }
            return instance!!

        }
    }

}