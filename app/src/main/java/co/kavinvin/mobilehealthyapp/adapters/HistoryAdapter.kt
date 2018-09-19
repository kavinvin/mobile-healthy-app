package co.kavinvin.mobilehealthyapp.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.models.Weight
import co.kavinvin.mobilehealthyapp.utils.longDateFormat
import kotlinx.android.synthetic.main.fragment_weight_history_item.view.*

class HistoryAdapter(val histories: List<Weight>) : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

    class HistoryHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(weight: Weight, befWeight: Weight) {
            view.apply {
                weight_history_item_weight.text = weight.weight.toString()
                weight_history_item_date.text = longDateFormat(weight.date)
                weight_history_item_status.apply {
                    val opts: Pair<String, Int> = when {
                        weight.weight > befWeight.weight -> "Increase" to ContextCompat.getColor(context, R.color.UP)
                        weight.weight < befWeight.weight -> "Decrease" to ContextCompat.getColor(context, R.color.DOWN)
                        else -> "" to ContextCompat.getColor(context, R.color.UP)
                    }
                    text = opts.first
                    setTextColor(opts.second)
                }
            }
        }

        fun bind(weight: Weight) {
            view.apply {
                weight_history_item_weight.text = weight.weight.toString()
                weight_history_item_date.text = longDateFormat(weight.date)
                weight_history_item_status.text = ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val historyItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_weight_history_item, parent, false)
        return HistoryHolder(historyItem)
    }

    override fun onBindViewHolder(holder: HistoryHolder, pos: Int) {
        if (pos != histories.size-1) {
            holder.bind(histories[pos], histories[pos+1])
        } else {
            holder.bind(histories[pos])
        }
    }

    override fun getItemCount(): Int = histories.size
}
