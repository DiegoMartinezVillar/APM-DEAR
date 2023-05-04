package ensemble.dear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ensemble.dear.database.entities.DeliveryEntity

import ensemble.dear.database.entities.PackageEntity

@Dao
interface PackageDAO {

    @Query("SELECT * FROM package_table ")
    fun getAllPackages(): LiveData<List<PackageEntity>>

    @Insert
    fun insert(packageEnt: PackageEntity)

    @Update
    fun update(packageEnt: PackageEntity)

    @Delete
    fun delete(packageEnt: PackageEntity)
}