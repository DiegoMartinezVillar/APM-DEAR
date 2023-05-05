package ensemble.dear.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ensemble.dear.database.converters.AuthorizedCourierConverter
import ensemble.dear.database.dao.AuthorizedCourierDAO
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entities.AuthorizedCourier
import ensemble.dear.database.entities.DeliveryEntity
import ensemble.dear.database.entities.PackageEntity
import ensemble.dear.database.repository.AuthorizedCourierRepository
import ensemble.dear.database.repository.PackageRepository
import ensemble.dear.currentTrackings.IN_DELIVERY_STATE
import ensemble.dear.currentTrackings.PRE_ADMISSION_STATE

private fun insertAuthorizedCouriers(context: Context) {
    val newCouriers = listOf(AuthorizedCourier("ensemble.dear.app@gmail.com"))
    val insertedCouriers = AuthorizedCourierRepository(context).getAllAuthorizedCouriers()

    for (courier in newCouriers) {
        if (courier !in insertedCouriers) {
            AuthorizedCourierRepository(context).insertAuthorizedCourier(courier)
        }
    }

}

private fun populateDatabase(context: Context) {
            var deliveryDAO = PackageRepository(context).getAll()

            var newPackages = listOf(
                PackageEntity(
                    123456789, "Alarm clock","436 Constitution Way San Francisco, California",
                    IN_DELIVERY_STATE, //LocalDate.of(2023, 4, 20),
                    "20th of april 2023",
                    1, "Aliexpress"
                ),
                PackageEntity(
                    121212121, "Bike shorts", "29 Idlewood Dr " +
                            "San Francisco, California", PRE_ADMISSION_STATE,
                    "13th of april 2023", 3, "Alibaba"
                )
            )

            for (courier in newPackages) {
                if (courier !in deliveryDAO) {
                    PackageRepository(context).insert(courier)
                }
            }
        }

@Database(entities = [AuthorizedCourier::class, PackageEntity::class, DeliveryEntity::class], version = 1, exportSchema = false)
@TypeConverters(AuthorizedCourierConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authorizedCourierDao(): AuthorizedCourierDAO
    abstract fun packageDAO(): PackageDAO
    abstract fun deliveryDAO(): DeliveryDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java, "database"
                    ).allowMainThreadQueries().build()
                }

                insertAuthorizedCouriers(context)
                populateDatabase(context)
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}