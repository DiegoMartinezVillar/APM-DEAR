package ensemble.dear.database.repository

import android.content.Context
import android.os.AsyncTask
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.AuthorizedCourierDAO
import ensemble.dear.database.entity.AuthorizedCourier



class AuthorizedCourierRepository(context: Context) {

    var db: AuthorizedCourierDAO = AppDatabase.getInstance(context).authorizedCourierDao()

    //Fetch All the AuthorizedCouriers
    fun getAllAuthorizedCouriers(): List<AuthorizedCourier> {
        return db.gelAllAuthorizedCouriers()
    }

    // Insert new AuthorizedCourier
    fun insertAuthorizedCourier(authorizedCourier: AuthorizedCourier) {
        insertAsyncTask(db).execute(authorizedCourier)
    }

    // update AuthorizedCourier
    fun updateAuthorizedCouriers(authorizedCourier: AuthorizedCourier) {
        db.updateAuthorizedCourier(authorizedCourier)
    }

    // Delete AuthorizedCourier
    fun deleteAuthorizedCourier(authorizedCourier: AuthorizedCourier) {
        db.deleteAuthorizedCourier(authorizedCourier)
    }

    private class insertAsyncTask internal constructor(private val authorizedCourierDao: AuthorizedCourierDAO) :
        AsyncTask<AuthorizedCourier, Void, Void>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: AuthorizedCourier): Void? {
            authorizedCourierDao.insertAuthorizedCourier(params[0])
            return null
        }
    }
}
