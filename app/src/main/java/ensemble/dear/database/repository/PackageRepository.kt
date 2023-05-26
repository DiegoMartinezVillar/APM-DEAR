package ensemble.dear.database.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.entity.Package
import java.time.LocalDate
import java.time.ZoneId

class PackageRepository(context: Context) {

    var packageDAO: PackageDAO = AppDatabase.getInstance(context).packageDAO()

    fun insert(packageEnt: Package) {
        insertAsyncTask(packageDAO).execute(packageEnt)
    }

    fun update(packageEnt: Package) {
        packageDAO.update(packageEnt)
    }

    fun updateSignature(signature: ByteArray, idPackage: Int) {
        packageDAO.updateSignature(signature, idPackage)
    }


    fun delete(packageEnt: Package) {
        packageDAO.delete(packageEnt)
    }

    fun deleteAll() {
        packageDAO.deleteAll()
    }

    fun getAll(): List<Package> {
        return packageDAO.getAllPackages()
    }

    fun getAllCourierPackagesForToday(idCourier: String): List<Package> {
        return packageDAO.getCourierPackagesForToday(idCourier,
            LocalDate.now(ZoneId.of("Europe/Madrid")).toString())
    }

    fun getCourierPastPackages(idCourier: String): List<Package> {
        return packageDAO.getPastPackages(idCourier)
    }

    fun getPackageByNumber(number: Int): Package {
        return packageDAO.getPackageByNumber(number)
    }

    fun getPackageDeliveryByNumber(number: Int): DeliveryPackage {
        return packageDAO.getPackageDeliveryByNumber(number)
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

    private class insertAsyncTask internal constructor(private val packageDAO: PackageDAO) :
        AsyncTask<Package, Void, Void>() {

        @Deprecated("Deprecated")
        override fun doInBackground(vararg params: Package): Void? {
            packageDAO.insert(params[0])
            return null
        }
    }

}