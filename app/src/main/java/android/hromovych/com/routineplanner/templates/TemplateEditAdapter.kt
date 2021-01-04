package android.hromovych.com.routineplanner.templates

import android.hromovych.com.routineplanner.Doing
import android.hromovych.com.routineplanner.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TemplateEditAdapter(var doings: List<Doing>, val onItemClickAction: (View, Doing) -> Unit) :
    RecyclerView.Adapter<TemplateEditAdapter.Holder>() {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.findViewById<TextView>(R.id.doing_title)
        var doing: Doing? = null

        init {
            itemView.findViewById<CheckBox>(R.id.doing_checkbox).visibility = View.GONE
            itemView.setOnClickListener {
                onItemClickAction(it, doing!!)
            }
        }

        fun bind(doing: Doing) {
            this.doing = doing
            titleView.text = doing.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_doing, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(doings[position])
    }

    override fun getItemCount() = doings.size

}