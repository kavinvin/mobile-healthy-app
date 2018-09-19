package co.kavinvin.mobilehealthyapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.models.Weight
import co.kavinvin.mobilehealthyapp.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_weight_form.*
import java.util.*

class WeightFormFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var date: Date = Date()
    private val calendar = Calendar.getInstance()

    private val onDateSet = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
    }

    private fun updateLabel() {
        date = calendar.time
        weight_form_date.setText(longDateFormat(calendar.time))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weight_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        weight_form_date.setOnClickListener {
            DatePickerDialog(context, onDateSet,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        weight_form_back_button.setOnClickListener {
            setFragment(MenuFragment())
        }
        weight_form_save_button.setOnClickListener {
            val weight = weight_form_weight.text.toString().toDoubleOrNull()

            if (weight == null) {
                toaster().requireAllFieldsToast.show()
                return@setOnClickListener
            }

            db.collection("myfitness")
                    .document(auth.currentUser!!.uid)
                    .collection("weight")
                    .document(shortDateFormat(date))
                    .set(Weight(weight, date))
                    .addOnSuccessListener {
                        toast("Success")
                    }
                    .addOnFailureListener {
                        toast("Failed")
                    }

        }
    }

}