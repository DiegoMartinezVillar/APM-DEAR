package ensemble.dear.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.android.gms.maps.model.LatLng
import ensemble.dear.database.converter.AuthorizedCourierConverter
import ensemble.dear.database.dao.AuthorizedCourierDAO
import ensemble.dear.database.converter.LocalDateConverter
import ensemble.dear.database.dao.DeliveryDAO
import ensemble.dear.database.dao.PackageDAO
import ensemble.dear.database.entity.AuthorizedCourier
import ensemble.dear.database.entity.Delivery
import ensemble.dear.database.entity.Package
import ensemble.dear.database.repository.AuthorizedCourierRepository
import ensemble.dear.database.repository.PackageRepository
import ensemble.dear.database.repository.DeliveryRepository
import java.time.LocalDate
import java.time.ZoneId

const val PRE_ADMISSION_STATE : String = "pre_admission"
const val ON_THE_WAY_STATE : String = "on_the_way"
const val IN_DELIVERY_STATE : String = "in_delivery"
const val DELIVERED_STATE : String = "delivered"


@Database(entities = [AuthorizedCourier::class, Package::class, Delivery::class], version = 13, exportSchema = false)
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


            //DeliveryRepository(context).deleteAll()
            //PackageRepository(context).deleteAll()

            val newPackages = listOf(
                Package(
                    123456789, //"Alarm clock",
                    "436 Constitution Way San Francisco, California",
                    DELIVERED_STATE, LocalDate.of(2023, 4, 20),
                    "ensemble.dear.app@gmail.com", "Aliexpress",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Leave at front door",
                    37.7557557, -122.4208508
                ),
                Package(
                    121212121, //"Bike shorts",
                    "29 Idlewood Dr San Francisco, California", PRE_ADMISSION_STATE,
                    LocalDate.of(2023, 4, 13),
                    "ensemble.dear.app@gmail.com", "Amazon",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                    "The house is next to John's bakery",
                    37.80764569999999, -122.4195251
                ),
                Package(
                    999999999,
                    "1233 Howard Street, San Francisco", IN_DELIVERY_STATE,
                    LocalDate.of(2023, 5, 19),
                    "ensemble.dear.app@gmail.com", "Aliexpress",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Send a message around ten minutes before arriving",
                    37.7757292, -122.4119508
                ),
                Package(
                    888888888,
                    "2715 Hyde Street, San Francisco", IN_DELIVERY_STATE,
                    LocalDate.of(2023, 5, 20),
                    "ensemble.dear.app@gmail.com", "Amazon",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                    "Call upon arrival",
                    37.8060487, -122.4206076
                ),
                Package(
                    777777777,
                    "C. Rey Abdullah, 15004 A Coruña, La Coruña", IN_DELIVERY_STATE,
                    LocalDate.of(2023, 5, 22),
                    "ensemble.dear.app@gmail.com", "Amazon",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                    "Leave on top of the doormat",
                    43.365482, -8.412086
                ),
                Package(
                    666666666,
                    "1233 Howard Street, San Francisco", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Aliexpress",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Send a message around ten minutes before arriving",
                    37.7757292, -122.4119508
                ),
                Package(
                    555555555,
                    "2715 Hyde Street, San Francisco", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Amazon",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                    "Call upon arrival",
                    37.8060487, -122.4206076
                ),
                Package(
                    444444444,
                    "Camiño do Lagar de Castro, 6, 15008 A Coruña", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Aliexpress",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Call Lazaro upon arrival",
                    43.332850, -8.410837
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