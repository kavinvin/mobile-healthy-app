package co.kavinvin.mobilehealthyapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.definitions.Toaster
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.setFragment(other: Fragment) {
    activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_view, other)
            .commit()
}

fun FragmentActivity.setFragment(other: Fragment) {
    supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_view, other)
            .commit()
}

fun Fragment.toast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toaster(): Toaster {
    return Toaster(context)
}

fun formatDate(format: String): (Date) -> String {
    val formatter = SimpleDateFormat(format, Locale.US)
    return { formatter.format(it) }
}

fun readDate(format: String): (String) -> Date {
    val formatter = SimpleDateFormat(format, Locale.US)
    return { formatter.parse(it) }
}

val longDateFormat = formatDate("dd MMMM yyyy")
val shortDateFormat = formatDate("yyyy-MM-dd")
