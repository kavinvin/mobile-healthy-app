package co.kavinvin.mobilehealthyapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.fragments.LoginFragment
import co.kavinvin.mobilehealthyapp.fragments.MenuFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth.currentUser?.also {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_view, MenuFragment())
                    .commit()
            return
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_view, LoginFragment())
                .commit()
    }

}
