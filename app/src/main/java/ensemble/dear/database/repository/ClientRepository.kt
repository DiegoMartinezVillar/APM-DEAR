package ensemble.dear.database.repository

import android.content.Context
import android.os.AsyncTask
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.dao.ClientDAO
import ensemble.dear.database.entity.Client


class ClientRepository(context: Context) {
    var db: ClientDAO = AppDatabase.getInstance(context)?.clientDAO()!!

    //Fetch All the AuthorizedCouriers
    fun getAllClients(): List<Client> {
        return db.getAllClients()
    }

    // Insert new AuthorizedCourier
    fun insert(client: Client) {
        insertAsyncTask(db).execute(client)
    }

    private class insertAsyncTask internal constructor(private val clientDAO: ClientDAO) :
        AsyncTask<Client, Void, Void>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Client): Void? {
            clientDAO.insert(params[0])
            return null
        }
    }
}