package co.kavinvin.mobilehealthyapp.definitions

import android.content.Context
import android.widget.Toast

class Toaster(private val context: Context?) {

    val requireAllFieldsToast = createToast("Please specify all fields")
    val loginSuccessToast = createToast("Login success")
    val verifyEmailToast = createToast("Please verify your email")
    val registrationSuccessToast = createToast("Registration success")
    val passwordTooShortToast = createToast("Password must be longer than 6 characters")
    val passwordNotMatchToast = createToast("Both passwords must be matched")
    fun maybeUnknownErrorToast(text: String?) = createToast(text ?: "Unknown error")

    private fun createToast(text: String): Toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)

}