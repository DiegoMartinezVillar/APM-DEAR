package ensemble.dear.database.dao

import androidx.room.*
import ensemble.dear.database.DELIVERED_STATE
import ensemble.dear.database.IN_DELIVERY_STATE
import ensemble.dear.database.entity.DeliveryPackage

import ensemble.dear.database.entity.Package

@Dao
interface PackageDAO {

    @Query("SELECT * FROM package_table ")
    fun getAllPackages(): List<Package>

    @Query("SELECT d.idDelivery as idDelivery, p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.shipperCompany as shipperCompany, " +
            " p.shipperCompanyPhoto as shipperCompanyPhoto, " +
            " p.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM package_table p " +
            " INNER JOIN delivery_table d on p.packageNumber = d.idPackage " +
            " WHERE arrivalDate = :today and idCourier = :idCourier")
    fun getAllCourierPackagesForToday(idCourier: String, today: String): List<DeliveryPackage>

    @Query("SELECT * " +
            " FROM package_table p " +
            " WHERE arrivalDate = :today and idCourier = :idCourier and p.state = '" + IN_DELIVERY_STATE + "' ")
    fun getCourierPackagesForToday(idCourier: String, today: String): List<Package>

    @Query("SELECT d.idDelivery as idDelivery, p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.shipperCompany as shipperCompany, " +
            " p.shipperCompanyPhoto as shipperCompanyPhoto, " +
            " p.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM package_table p " +
            " JOIN delivery_table d on p.packageNumber = d.idPackage WHERE p.packageNumber = :number")
    fun getPackageDeliveryByNumber(number: Int): DeliveryPackage

    @Query("SELECT * FROM package_table p WHERE p.packageNumber = :number and p.state != '" + DELIVERED_STATE + "'")
    fun getPackageByNumber(number: Int): Package


    @Query("SELECT * " +
            " FROM package_table p " +
            " WHERE p.idCourier = :idCourier and p.state = '" + DELIVERED_STATE + "'")
    fun getPastPackages(idCourier: String): List<Package>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(packageEnt: Package)

    @Update
    fun update(packageEnt: Package)

    @Query("UPDATE package_table SET signature = :signature, state = '"+ DELIVERED_STATE + "' WHERE packageNumber = :idPackage")
    fun updateSignature(signature: ByteArray, idPackage: Int)

    @Delete
    fun delete(packageEnt: Package)

    @Query("DELETE FROM package_table ")
    fun deleteAll()
}