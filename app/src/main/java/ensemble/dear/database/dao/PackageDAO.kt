package ensemble.dear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ensemble.dear.database.entities.PackageEntity

@Dao
interface PackageDAO {

    @Query("SELECT * FROM package_table ")
    fun getAllPackages(): LiveData<List<PackageEntity>>
    //suspend

    @Insert
    fun insert(packageEnt: PackageEntity)


}