package co.kavinvin.mobilehealthyapp

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class MenuAdapter(private val dataset: ArrayList<MenuItem>,
                  private val eachMenuClickListener: (MenuItem) -> Unit)
    : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {
        fun bind(menu: MenuItem, listener: (MenuItem) -> Unit) {
            view.text = menu.name
            view.setOnClickListener { listener(menu) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return MenuViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, pos: Int) {
        holder.bind(dataset[pos], eachMenuClickListener)
    }

    override fun getItemCount(): Int = dataset.size
}


data class MenuItem(val fragment: Fragment, val name: String)