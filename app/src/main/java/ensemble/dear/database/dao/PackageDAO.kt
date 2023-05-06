package ensemble.dear.database.dao

import androidx.room.*
import ensemble.dear.database.entity.DeliveryPackage

import ensemble.dear.database.entity.Package

@Dao
interface PackageDAO {

    @Query("SELECT * FROM package_table ")
    fun getAllPackages(): List<Package>

    @Query("SELECT * FROM package_table p WHERE p.packageNumber = :number")
    fun getPackageByNumber(number: Int): Package

    @Insert
    fun insert(packageEnt: Package)

    @Update
    fun update(packageEnt: Package)

    @Delete
    fun delete(packageEnt: Package)
}