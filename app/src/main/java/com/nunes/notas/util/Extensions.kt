
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.nunes.notas.R
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

private fun Activity.sharedPreferences(): SharedPreferences? {
    val sharedPreferences = getSharedPreferences("app_notas", MODE_PRIVATE)
    return  sharedPreferences
}

fun Activity.setSharedPreferences(key: String, value: Boolean): Boolean {
    try {
        sharedPreferences()?.edit()?.putBoolean(key, value)?.apply()
        return true
    } catch (e: Exception) {
        return false
    }
}

fun Activity.getSharedPreferences(key: String): Boolean? {
    return sharedPreferences()?.getBoolean(key, false)
}

fun Activity.toggleModeLightOrDark(): Boolean {
    val modeDarkIsActive = getSharedPreferences("mode_active") ?: false
    setSharedPreferences("mode_active", !modeDarkIsActive)
    if(!modeDarkIsActive){
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    return !modeDarkIsActive
}

fun Activity.checkThemeActive() {
    val modeDarkIsActive = getSharedPreferences("mode_active") ?: false
    if (modeDarkIsActive) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
    }
}

fun Activity.checkIconThemeMode(menu: Menu) {
    val menuThemeMode = menu.findItem(R.id.menuThemeMode)

    val modeDarkIsActive = getSharedPreferences("mode_active") ?: false
    if (modeDarkIsActive) {
        menuThemeMode.setIcon(R.drawable.ic_light_mode_24)
    } else {
        menuThemeMode.setIcon(R.drawable.ic_dark_mode_24)
    }
}