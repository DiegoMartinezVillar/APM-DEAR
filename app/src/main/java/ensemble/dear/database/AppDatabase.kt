package ensemble.dear.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ensemble.dear.database.converters.AuthorizedCourierConverter
import ensemble.dear.database.dao.AuthorizedCourierDAO
import androidx.sqlite.db.SupportSQLiteDatabase
import ensemble.dear.currentTrackings.IN_DELIVERY_STATE
import ensemble.dear.currentTrackings.PRE_ADMISSION_STATE
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entities.AuthorizedCourier
import ensemble.dear.database.entities.DeliveryEntity
import ensemble.dear.database.entities.PackageEntity
import ensemble.dear.database.repository.PackageRepository
import java.time.LocalDate
import ensemble.dear.database.repository.AuthorizedCourierRepository

fun insertAuthorizedCouriers(context: Context) {
    val newCouriers = listOf(AuthorizedCourier("ensemble.dear.app@gmail.com"))
    val insertedCouriers = AuthorizedCourierRepository(context).getAllAuthorizedCouriers()

@Database(entities = [DeliveryEntity::class, PackageEntity::class], version = 2)
    for (courier in newCouriers) {
        if (courier !in insertedCouriers) {
            AuthorizedCourierRepository(context).insertAuthorizedCourier(courier)
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
                    .build()

                populateDatabase(ctx)
                insertAuthorizedCouriers(ctx)
            }
            return instance!!

        }

        fun destroyInstance() {
            instance = null
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
    }
}