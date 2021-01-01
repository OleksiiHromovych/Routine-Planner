package android.hromovych.com.routineplanner.doings

import android.hromovych.com.routineplanner.Doing
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.strike
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoingsAdapter(var doings: List<Doing>): RecyclerView.Adapter<DoingsAdapter.Holder>() {
    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val statusCheckBox = itemView.findViewById<CheckBox>(R.id.doing_checkbox)
        val titleView = itemView.findViewById<TextView>(R.id.doing_title)

        init {
            statusCheckBox.setOnCheckedChangeListener { _, checked ->
                titleView.strike = checked
            }
        }

        fun bind(doing: Doing){
            statusCheckBox.isChecked = doing.isCompleted

            titleView.text = doing.title
            titleView.strike = doing.isCompleted

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doing, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(doings[position])
    }

    override fun getItemCount() = doings.size

}