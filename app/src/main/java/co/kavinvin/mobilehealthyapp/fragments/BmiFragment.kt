package co.kavinvin.mobilehealthyapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.utils.setFragment
import co.kavinvin.mobilehealthyapp.utils.toaster
import kotlinx.android.synthetic.main.fragment_bmi.*
import kotlin.math.pow

class BmiFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bmi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bmi_back_btn.setOnClickListener { setFragment(MenuFragment()) }
        bmi_calculate_btn.setOnClickListener {
            val weight = bmi_weight.text.toString().trim().toDoubleOrNull()
            val height = bmi_height.text.toString().trim().toDoubleOrNull()

            if (weight == null || height == null) {
                toaster().requireAllFieldsToast.show()
                return@setOnClickListener
            }

            val bmi = Math.sqrt(weight / (height / 100))
            bmi_result.text = String.format("%.02f", bmi)
        }
    }

}
