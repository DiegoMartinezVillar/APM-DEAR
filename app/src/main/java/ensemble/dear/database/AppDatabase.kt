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

fun insertAuthorizedCouriers(context: Context) {
    val newCouriers = listOf(AuthorizedCourier("ensemble.dear.app@gmail.com"))
    val insertedCouriers = AuthorizedCourierRepository(context).getAllAuthorizedCouriers()

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
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java, "user.db"
                    ).allowMainThreadQueries().build()
                }

                insertAuthorizedCouriers(context)
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}