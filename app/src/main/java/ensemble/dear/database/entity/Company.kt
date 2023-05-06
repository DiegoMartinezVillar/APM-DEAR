package ensemble.dear.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_table")
data class Company(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val companyName: String,
    val photo: String

) { }