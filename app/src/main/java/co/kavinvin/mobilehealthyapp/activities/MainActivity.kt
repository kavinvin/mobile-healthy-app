package co.kavinvin.mobilehealthyapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.fragments.LoginFragment
import co.kavinvin.mobilehealthyapp.fragments.MenuFragment
import co.kavinvin.mobilehealthyapp.utils.setFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (auth.currentUser == null) {
            setFragment(LoginFragment())
        } else {
            setFragment(MenuFragment())
        }
    }

}
