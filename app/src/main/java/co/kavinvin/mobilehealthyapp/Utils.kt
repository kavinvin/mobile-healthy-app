package co.kavinvin.mobilehealthyapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.Toast

fun Fragment.toast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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
