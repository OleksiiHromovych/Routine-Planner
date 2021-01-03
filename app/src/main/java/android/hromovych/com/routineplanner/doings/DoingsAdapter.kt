package android.hromovych.com.routineplanner.doings

import android.hromovych.com.routineplanner.Doing
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.isDarkModeOn
import android.hromovych.com.routineplanner.strike
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoingsAdapter(var doings: List<Doing>, val onTitleClickAction: (View, Doing) -> Unit) :
    RecyclerView.Adapter<DoingsAdapter.Holder>() {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val statusCheckBox = itemView.findViewById<CheckBox>(R.id.doing_checkbox)
        val titleView = itemView.findViewById<TextView>(R.id.doing_title)
        var doing: Doing? = null

        init {
            statusCheckBox.setOnCheckedChangeListener { _, checked ->
                doing?.isCompleted = checked
                setStatusUi(checked)
            }
            titleView.setOnClickListener {
                onTitleClickAction(it, doing!!)
            }
        }

        fun bind(doing: Doing) {
            this.doing = doing
            titleView.text = doing.title
            statusCheckBox.isChecked = doing.isCompleted

            setStatusUi(statusCheckBox.isChecked)
        }

        private fun setStatusUi(status: Boolean) {
            titleView.strike = status
            val backgroundColor = if (!status)
                android.R.color.transparent
            else {
                if (itemView.context.isDarkModeOn()) {
                    R.color.doing_completed_background_night
                } else {
                    R.color.doing_completed_background
                }
            }
            itemView.setBackgroundResource(backgroundColor)

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