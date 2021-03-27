package android.hromovych.com.routineplanner.defaults

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.doings.Doing
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DefaultDoingRecyclerAdapter(
    doings: List<Doing>,
    val onItemClickAction: (View, Doing) -> Unit
) :
    RecyclerView.Adapter<DefaultDoingRecyclerAdapter.Holder>() {
    var doings = doings
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.findViewById<TextView>(R.id.doing_title)
        var doing: Doing? = null

        init {
            itemView.findViewById<CheckBox>(R.id.doing_checkbox).visibility = View.GONE
            itemView.setOnClickListener {
                onItemClickAction(it, doing!!)
            }
        }

        fun bind(position: Int) {
            itemView.setBackgroundResource(
                if (position % 2 == 0)
                    R.color.application_background_second
                else
                    R.color.application_background
            )
            doing = doings[position].apply {
                titleView.text = title
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_doing, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = doings.size
}