package ensemble.dear.database.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entity.Package

class PackageRepository(context: Context) {

    var packageDAO: PackageDAO = AppDatabase.getInstance(context)?.packageDAO()!!

    fun insert(packageEnt: Package) {
        insertAsyncTask(packageDAO).execute(packageEnt)
    }

    fun delete(packageEnt: Package) {
        packageDAO.delete(packageEnt)
    }

    fun getAll(): List<Package> {
        return packageDAO.getAllPackages()
    }

    fun getPackageByNumber(number: Int): Package {
        return packageDAO.getPackageByNumber(number)
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