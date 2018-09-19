package co.kavinvin.mobilehealthyapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.adapters.HistoryAdapter
import co.kavinvin.mobilehealthyapp.models.Weight
import co.kavinvin.mobilehealthyapp.utils.setFragment
import co.kavinvin.mobilehealthyapp.utils.toaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_weight_history.*
import java.util.*

class WeightHistoryFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
//    private var histories: List<Weight> = List.Nil.instance()
    private val histories = ArrayList<Weight>()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var divider: DividerItemDecoration
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutManager = LinearLayoutManager(context)
        divider = DividerItemDecoration(context, layoutManager.orientation)
        return inflater.inflate(R.layout.fragment_weight_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        weight_history_back_button.setOnClickListener { setFragment(WeightFormFragment()) }

        db.collection("myfitness")
                .document(auth.currentUser!!.uid)
                .collection("weight")
                .orderBy("date")
                .get()
                .addOnSuccessListener { qsnap ->
                    qsnap.forEach { snap ->
                        Log.i("HIST", "${snap.id}: ${snap.data}")
                        val weight = Weight(snap.get("weight") as Double, snap.get("date") as Date)
                        histories.add(weight)
                    }
                    Log.i("HIST", histories.toString())
                    fetchSuccess()
                }
                .addOnFailureListener {
                    toaster().maybeUnknownErrorToast(it.message).show()
                }
    }

    private fun fetchSuccess() {
        histories.reverse()
        historyAdapter = HistoryAdapter(histories)
        weight_history_list?.apply {
            adapter = historyAdapter
            addItemDecoration(divider)
            layoutManager = this@WeightHistoryFragment.layoutManager
        }
    }
}