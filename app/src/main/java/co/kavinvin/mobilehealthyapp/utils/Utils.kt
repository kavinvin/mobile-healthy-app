package co.kavinvin.mobilehealthyapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.definitions.Toaster
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.toast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toaster(): Toaster {
    return Toaster(context)
}

fun Fragment.goTo(other: Fragment) {
    activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_view, other)
            .commit()
}

fun FragmentActivity.goTo(other: Fragment) {
    supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_view, other)
            .commit()
}

fun formatDate(format: String): (Date) -> String {
    val formatter = SimpleDateFormat(format, Locale.US)
    return { formatter.format(it) }
}

fun     readDate(format: String): (String) -> Date {
    val formatter = SimpleDateFormat(format, Locale.US)
    return { formatter.parse(it) }
}