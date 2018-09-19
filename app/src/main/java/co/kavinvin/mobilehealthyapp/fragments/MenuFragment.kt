package co.kavinvin.mobilehealthyapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.adapters.MenuAdapter
import co.kavinvin.mobilehealthyapp.adapters.MenuItem
import co.kavinvin.mobilehealthyapp.utils.goTo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        menuAdapter = MenuAdapter(menuList) { menuItem ->
            if (menuItem.name == "Log out") {
                auth.signOut()
            }
            goTo(menuItem.fragment)
        }
        linearLayoutManager = LinearLayoutManager(context)
        divider = DividerItemDecoration(context, linearLayoutManager.orientation)

        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        menu_list.apply {
            adapter = menuAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(divider)
        }
    }

    private lateinit var menuAdapter: MenuAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var divider: DividerItemDecoration
    private val auth = FirebaseAuth.getInstance()

    private val menuList = arrayListOf(
            MenuItem(LoginFragment(), "Log out"),
            MenuItem(WeightHistoryFragment(), "Weight history"),
            MenuItem(WeightFormFragment(), "Add weight record"))

}
