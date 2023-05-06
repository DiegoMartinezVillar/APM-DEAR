package ensemble.dear.database.dao

import androidx.room.*

import ensemble.dear.database.entity.Delivery;
import ensemble.dear.database.entity.DeliveryPackage

@Dao
interface DeliveryDAO {

    @Query("SELECT * FROM delivery_table ")
    fun getAllDeliveries(): List<Delivery>

    @Query("SELECT p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.packageContent as packageContent, p.shipperCompany as shipperCompany, " +
            " d.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM delivery_table d " +
            " JOIN package_table p on p.packageNumber = d.idPackage" ) //+
            //" WHERE d.idClient = ")
    fun getPackagesUser(): List<DeliveryPackage>

    @Query("SELECT p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.packageContent as packageContent, p.shipperCompany as shipperCompany, " +
            " d.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM delivery_table d " +
            " JOIN package_table p on p.packageNumber = d.idPackage" +
            " WHERE d.idPackage = :idPackageSearch" ) //+
    //" WHERE d.idClient = ")
    fun getPackagesById(idPackageSearch: Int): DeliveryPackage


    @Insert
    fun insert(delivery: Delivery)

    @Update
    fun update(delivery: Delivery)

    @Delete
    fun delete(delivery: Delivery)

}