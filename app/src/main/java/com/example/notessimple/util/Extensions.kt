
import android.icu.text.SimpleDateFormat
import java.util.Calendar

fun returnDaysAgo(day: Int) : Long {
    return Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH, - day)
    }.timeInMillis
}

fun formatDateDayMonthYear(date : Long): String {
    return SimpleDateFormat("dd/MM/yyyy").format(date)
}

fun Long.formatDateCustom(): String {

    var formatter = SimpleDateFormat("dd/MM/yyyy")
    val dateReceived = formatter.format(this)

    var counter = 0

    while (counter < 7) {
        if (dateReceived == formatDateDayMonthYear( returnDaysAgo( counter ))) {
            formatter = when(counter) {
                0               -> SimpleDateFormat("HH:mm")
                in 1..6   -> SimpleDateFormat("E HH:mm")
                else            -> SimpleDateFormat("dd/MM/yyyy")
            }
        }
        counter ++
    }

    return  formatter.format(this)
}