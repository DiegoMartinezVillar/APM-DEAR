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


@Database(entities = [AuthorizedCourier::class, Package::class, Delivery::class], version = 14, exportSchema = false)
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

            val newPackages = listOf(
                Package(
                    111111111,
                    "CHUAC, 84, 15006 A Coruña,", ON_THE_WAY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")).plusDays(2),
                    "ensemble.dear.app@gmail.com", "SEUR",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Leave in floor 0",
                    43.344263, -8.389146, null
                ),
                Package(
                    121212121,
                    "Rúa do Socorro, 6, 15003 A Coruña", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "SEUR",
                    "https://upload.wikimedia.org/wikipedia/commons/5/57/SEUR_logo.svg",
                    "The house is next to John's bakery",
                    43.371849, -8.402275, null
                ),
                Package(
                    222222222,
                    "Estación, Ronda de Outeiro, 15007 La Coruña", ON_THE_WAY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")).plusDays(2),
                    "ensemble.dear.app@gmail.com", "SEUR",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Text 10 minutes before arriving",
                    43.352703, -8.408245, null
                ),
                Package(
                    333333333,
                    "Avda. Salvador de Madariaga, 9, 15008 A Coruña", PRE_ADMISSION_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")).plusDays(3),
                    "ensemble.dear.app@gmail.com", "SEUR",
                    "https://upload.wikimedia.org/wikipedia/commons/5/57/SEUR_logo.svg",
                    "",
                    43.348666, -8.40548, null
                ),
                Package(
                    123456789,
                    "Avda. Puerto de A Coruña, 3, 15003 A Coruña", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Aliexpress",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Leave at front door",
                    43.3682, -8.399812, null
                ),

                Package(
                    999999999,
                    "Rúa Manuel Murguía, 1, 15012 A Coruña", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "SEUR",
                    "https://upload.wikimedia.org/wikipedia/commons/5/57/SEUR_logo.svg",
                    "Send a message around ten minutes before arriving",
                    43.368716 , -8.41751, null
                ),
                Package(
                    888888888,
                    "2715 Hyde Street, San Francisco", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Amazon",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                    "Call upon arrival",
                    37.8060487, -122.4206076, null
                ),
                Package(
                    777777777,
                    "C. Rey Abdullah, 15004 A Coruña, La Coruña", DELIVERED_STATE,
                    LocalDate.of(2023, 5, 22),
                    "ensemble.dear.app@gmail.com", "Amazon",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                    "Leave on top of the doormat",
                    43.365482, -8.412086, null
                ),
                Package(
                    666666666,
                    "Rúa Francisco Pérez Carballo, 5, 15008 A Coruña", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Aliexpress",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Send a message around ten minutes before arriving",
                    43.33877, -8.408379, null
                ),

                Package(
                    555555555,
                    "Camiño da Grela ao Martinete, 15008 A Coruña", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Amazon",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                    "Call upon arrival",
                    43.346169, -8.427221, null
                ),
                Package(
                    444444444,
                    "Camiño do Lagar de Castro, 6, 15008 A Coruña", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Aliexpress",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3b/Aliexpress_logo.svg",
                    "Call Lazaro upon arrival",
                    43.332850, -8.410837, null
                ),
                Package(
                    888888888,
                    "Estrada da Torre de Hércules, 1, 15002 A Coruña", IN_DELIVERY_STATE,
                    LocalDate.now(ZoneId.of("Europe/Madrid")),
                    "ensemble.dear.app@gmail.com", "Amazon",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                    "Call upon arrival",
                    43.385942, -8.406524, null
                ),

            )

            for (packageItem in newPackages) {
                PackageRepository(context).insert(packageItem)
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