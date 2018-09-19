package co.kavinvin.mobilehealthyapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.utils.goTo
import co.kavinvin.mobilehealthyapp.utils.toast
import co.kavinvin.mobilehealthyapp.utils.toaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        login_login_button.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_password.text.toString()

            when {
                listOf(email, password).any { it.isEmpty() } -> toast("All fields are required")
                else -> login(email, password)
            }
        }

        login_register_button.setOnClickListener {
            activity!!.goTo(RegisterFragment())
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    if (it.user.isEmailVerified) {
                        toaster().loginSuccessToast.show()
                        goTo(MenuFragment())
                    } else {
                        toaster().verifyEmailToast.show()
                        auth.signOut()
                    }
                }
                .addOnFailureListener {
                    toast(it.message ?: "Unknown error")
                }
//        db.collection("history")
//                .document("ayxyTVfLhS20bO9XGLrc")
//                .set(mapOf(
//                        "weight" to 10,
//                        "date" to "1000"
//                ))
//                .addOnSuccessListener {
//                    Log.i("HISTORY", "Success")
//                    toast("Success!!")
//                }
//                .addOnFailureListener {
//                    Log.i("HISTORY", "Failed")
//                    toast(it.message ?: "Unknown error.")
//                }
//        Log.i("HISTORY", "Done")

    }

}