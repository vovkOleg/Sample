package mock.brains.mvvmappskeleton.core.extension

import java.time.*
import java.util.*

// Date
fun Date.toLocalDate(): LocalDate {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.toLocalDate()
}

fun Date.toLocalDateTime(): LocalDateTime {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.toLocalDateTime()
}

fun Date.toThreeTenInstant(): Instant {
    return Instant.ofEpochMilli(this.time)
}

// Calendar
fun Calendar.toZonedDateTime(): ZonedDateTime {
    val zoneId = ZoneId.of(timeZone.id)
    val instant = Instant.ofEpochMilli(this.time.time)
    return ZonedDateTime.ofInstant(instant, zoneId)
}

fun Calendar.toLocalDate(): LocalDate {
    //gotta add one to the cal month since it starts at 0
    val monthOfYear = get(Calendar.MONTH) + 1
    return LocalDate.of(get(Calendar.YEAR), monthOfYear, get(Calendar.DAY_OF_MONTH))
}

fun Calendar.toLocalDateTime(): LocalDateTime {
    val monthOfYear = get(Calendar.MONTH) + 1
    return LocalDateTime.of(get(Calendar.YEAR), monthOfYear, get(Calendar.DAY_OF_MONTH), get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE), get(Calendar.SECOND))
}

// LocalDate
fun LocalDate.toDateAtStartOfDay(zoneId: ZoneId = ZoneId.systemDefault()): Date {
    return Date(atStartOfDay(zoneId).toInstant().toEpochMilli())
}

// LocalDateTime
fun LocalDateTime.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.set(Calendar.YEAR, year)
    cal.set(Calendar.MONTH, month.value - 1)
    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    cal.set(Calendar.HOUR_OF_DAY, hour)
    cal.set(Calendar.MINUTE, minute)
    cal.set(Calendar.SECOND, second)
    return cal
}

// LocalDateTime
fun LocalDateTime.toMillis(): Long? = atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()