package ensemble.dear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ensemble.dear.database.entities.CompanyEntity

import ensemble.dear.database.entities.DeliveryEntity;

@Dao
interface CompanyDAO {

    @Query("SELECT * FROM company_table ")
    fun getAllCompanies(): LiveData<List<CompanyEntity>>

    @Insert
    fun insert(company: CompanyEntity)

    @Update
    fun update(company: CompanyEntity)

    @Delete
    fun delete(company: CompanyEntity)

}