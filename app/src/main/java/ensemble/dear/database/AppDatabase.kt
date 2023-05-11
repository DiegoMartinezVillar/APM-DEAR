package ensemble.dear.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ensemble.dear.database.converter.AuthorizedCourierConverter
import ensemble.dear.database.dao.AuthorizedCourierDAO
import ensemble.dear.database.converter.LocalDateConverter
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entity.*
import ensemble.dear.database.repository.PackageRepository
import ensemble.dear.database.repository.AuthorizedCourierRepository
import java.time.LocalDate

const val PRE_ADMISSION_STATE : String = "pre_admission"
const val ON_THE_WAY_STATE : String = "on_the_way"
const val IN_DELIVERY_STATE : String = "in_delivery"
const val DELIVERED_STATE : String = "delivered"


@Database(entities = [AuthorizedCourier::class, Package::class, Delivery::class], version = 9, exportSchema = false)
@TypeConverters(AuthorizedCourierConverter::class, LocalDateConverter::class)
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
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                insertPreloadedPackages(ctx)
                insertAuthorizedCouriers(ctx)
            }
            return instance!!

        }

        fun destroyInstance() {
            instance = null
        }

        private fun insertPreloadedPackages(context: Context) {
            val deliveryDAO = PackageRepository(context).getAll()

            val newPackages = listOf(
                Package(
                    123456789, //"Alarm clock",
                    "436 Constitution Way San Francisco, California",
                    IN_DELIVERY_STATE, LocalDate.of(2023, 4, 20),
                    1, "Aliexpress", ""
                ),
                Package(
                    121212121, //"Bike shorts",
                    "29 Idlewood Dr San Francisco, California", PRE_ADMISSION_STATE,
                    LocalDate.of(2023, 4, 13),
                    3, "Alibaba", ""
                )
            )

            for (courier in newPackages) {
                if (courier !in deliveryDAO) {
                    PackageRepository(context).insert(courier)
                }
            }
        }


        private fun insertAuthorizedCouriers(context: Context) {
            val newCouriers = listOf(AuthorizedCourier("ensemble.dear.app@gmail.com"))
            val insertedCouriers = AuthorizedCourierRepository(context).getAllAuthorizedCouriers()

            for (courier in newCouriers) {
                if (courier !in insertedCouriers) {
                    AuthorizedCourierRepository(context).insertAuthorizedCourier(courier)
                }
            }

        }
    }
}