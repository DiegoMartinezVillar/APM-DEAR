package ensemble.dear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ensemble.dear.database.entity.Company

@Dao
interface CompanyDAO {

    @Query("SELECT * FROM company_table ")
    fun getAllCompanies(): LiveData<List<Company>>

    @Insert
    fun insert(company: Company)

    @Update
    fun update(company: Company)

    @Delete
    fun delete(company: Company)

}