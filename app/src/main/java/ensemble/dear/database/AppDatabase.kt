package ensemble.dear.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ensemble.dear.currentTrackings.IN_DELIVERY_STATE
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entities.DeliveryEntity
import ensemble.dear.database.entities.PackageEntity
import java.time.LocalDate

@Database(entities = [DeliveryEntity::class, PackageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun deliveriesDAO(): DeliveryDAO
    abstract fun packageDAO(): PackageDAO


    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "trackings_database"
                )
                        //.allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

                populateDatabase(instance!!)
            }
            return instance!!

        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: AppDatabase) {
            val deliveryDAO = db.deliveriesDAO()
            val packageDAO = db.packageDAO()


            //subscribeOnBackground {
            packageDAO.insert(
                PackageEntity(
                    123456789, "436 Constitution Way San Francisco, California",
                    IN_DELIVERY_STATE, //LocalDate.of(2023, 4, 20),
                    1
                )
            )

            //}
        }
    }
}