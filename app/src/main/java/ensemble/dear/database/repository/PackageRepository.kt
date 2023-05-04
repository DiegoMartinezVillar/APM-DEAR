package ensemble.dear.database.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entities.PackageEntity

class PackageRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)

    var packageDAO: PackageDAO = AppDatabase.getInstance(context)?.packageDAO()!!

    fun insert(packageEnt: PackageEntity) {
        insertAsyncTask(packageDAO).execute(packageEnt)
    }

    fun delete(packageEnt: PackageEntity) {
        packageDAO.delete(packageEnt)
    }

    fun getAll(): List<PackageEntity> {
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

    private class insertAsyncTask internal constructor(private val packageDAO: PackageDAO) :
        AsyncTask<PackageEntity, Void, Void>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: PackageEntity): Void? {
            packageDAO.insert(params[0])
            return null
        }
    }

}