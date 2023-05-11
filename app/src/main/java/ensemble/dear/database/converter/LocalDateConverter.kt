package ensemble.dear.database.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateConverter {
    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else {
            LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.toString()
    }
}