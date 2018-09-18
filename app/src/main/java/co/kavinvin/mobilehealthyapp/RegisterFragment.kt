package co.kavinvin.mobilehealthyapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_button.setOnClickListener {
            val email = registration_email.text.toString()
            val password = registration_password.text.toString()
            val confirmationPassword = registration_confirmation_password.text.toString()
            when {
                listOf(email, password, confirmationPassword).any { it.isEmpty() } ->
                    toast("All fields are required")
                password.length < 6 -> toast("Password must be longer than 6 characters")
                password != confirmationPassword -> toast("Both password must be matched")
                else -> register(email, password)
            }
        }

    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user.sendEmailVerification()
                    auth.signOut()
                    goTo(LoginFragment())
                    toast("Registration success!")
                }
                .addOnFailureListener {
                    toast(it.message ?: "Unknown error.")
                }
    }


}