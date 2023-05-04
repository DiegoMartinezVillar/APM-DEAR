package ensemble.dear.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_table")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val companyName: String,
    val photo: String

) { }