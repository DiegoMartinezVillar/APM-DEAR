package ensemble.dear.database.dao

import androidx.room.*

import ensemble.dear.database.entity.Delivery;
import ensemble.dear.database.entity.DeliveryPackage

@Dao
interface DeliveryDAO {

    @Query("SELECT * FROM delivery_table ")
    fun getAllDeliveries(): List<Delivery>

    @Query("SELECT d.idDelivery as idDelivery, p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.packageContent as packageContent, p.shipperCompany as shipperCompany, " +
            " d.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM delivery_table d " +
            " JOIN package_table p on p.packageNumber = d.idPackage " +
            " WHERE d.idClient = :idUser")
    fun getPackagesUser(idUser: String): List<DeliveryPackage>

    @Query("SELECT d.idDelivery as idDelivery, p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.packageContent as packageContent, p.shipperCompany as shipperCompany, " +
            " d.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM delivery_table d " +
            " JOIN package_table p on p.packageNumber = d.idPackage" +
            " WHERE d.idPackage = :idPackageSearch" )
    fun getPackageById(idPackageSearch: Int): DeliveryPackage


    @Query("SELECT d.idDelivery as idDelivery, p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.packageContent as packageContent, p.shipperCompany as shipperCompany, " +
            " d.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM delivery_table d " +
            " JOIN package_table p on p.packageNumber = d.idPackage " +
            " WHERE d.idClient = :idUser")
    // TODO añadir restricción de fecha pasada. DeliveryPackage vs Delivery
    fun getPastPackages(idUser: String): List<DeliveryPackage>


    @Insert
    fun insert(delivery: Delivery)

    @Update
    fun update(delivery: Delivery)

    @Query("DELETE FROM delivery_table WHERE idDelivery = :idDelivery")
    fun delete(idDelivery: Int)

}