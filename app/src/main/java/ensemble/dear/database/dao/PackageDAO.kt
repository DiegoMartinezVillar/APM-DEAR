package ensemble.dear.database.dao

import androidx.room.*
import ensemble.dear.database.entity.DeliveryPackage

import ensemble.dear.database.entity.Package

@Dao
interface PackageDAO {

    @Query("SELECT * FROM package_table ")
    fun getAllPackages(): List<Package>

    @Query("SELECT d.idDelivery as idDelivery, p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.shipperCompany as shipperCompany, " +
            " p.shipperCompanyPhoto as shipperCompanyPhoto, " +
            " d.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM package_table p " +
            " INNER JOIN delivery_table d on p.packageNumber = d.idPackage " +
            " WHERE arrivalDate = :today and idCourier = :idCourier")
    fun getAllCourierPackagesForToday(idCourier: String, today: String): List<DeliveryPackage>

    @Query("SELECT * " +
            " FROM package_table p " +
            " WHERE arrivalDate = :today and idCourier = :idCourier")
    fun getCourierPackagesForToday(idCourier: String, today: String): List<Package>

    @Query("SELECT d.idDelivery as idDelivery, p.packageNumber as packageNumber, p.address as address, p.state as state, " +
            " p.arrivalDate as arrivalDate, p.shipperCompany as shipperCompany, " +
            " p.shipperCompanyPhoto as shipperCompanyPhoto, " +
            " d.additionalInstructions as additionalInstructions, d.packageAlias as packageAlias " +
            " FROM package_table p " +
            " JOIN delivery_table d on p.packageNumber = d.idPackage WHERE p.packageNumber = :number")
    fun getPackageDeliveryByNumber(number: Int): DeliveryPackage

    @Query("SELECT * FROM package_table p WHERE p.packageNumber = :number")
    fun getPackageByNumber(number: Int): Package

    @Insert
    fun insert(packageEnt: Package)

    @Update
    fun update(packageEnt: Package)

    @Delete
    fun delete(packageEnt: Package)

    @Query("DELETE FROM package_table ")
    fun deleteAll()
}