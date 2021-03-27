package android.hromovych.com.routineplanner.doings

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.utils.strike
import android.hromovych.com.routineplanner.utils.toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoingsAdapter(var doings: List<Doing>, val onTitleClickAction: (View, Doing) -> Unit,
                    val onChangedCallback: (Doing) -> Unit) :
    RecyclerView.Adapter<DoingsAdapter.Holder>() {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moveImage = itemView.findViewById<ImageView>(R.id.doing_move_image_view)
        val statusCheckBox = itemView.findViewById<CheckBox>(R.id.doing_checkbox)
        val titleView = itemView.findViewById<TextView>(R.id.doing_title)
        var doing: Doing? = null

        init {
            statusCheckBox.setOnCheckedChangeListener { _, checked ->
                doing?.isCompleted = checked
                doing?.let { onChangedCallback(it) }
                setStatusUi(checked)
            }
            titleView.setOnClickListener {
                onTitleClickAction(it, doing!!)
            }
            moveImage.setOnLongClickListener {
                it.context.toast("Long click")
                true
            }
        }

        fun bind(position: Int) {
            this.doing = doings[position].apply {
                titleView.text = title
                statusCheckBox.isChecked = isCompleted
            }
            itemView.setBackgroundResource(
                if (position % 2 == 0)
                    R.color.application_background_second
                else
                    R.color.application_background
            )
            setStatusUi(statusCheckBox.isChecked)
        }

        private fun setStatusUi(status: Boolean) {
            titleView.strike = status
//            val backgroundColor = if (!status)
//                android.R.color.transparent
//            else {
//                R.color.doing_completed_background
//            }
//            itemView.setBackgroundResource(backgroundColor)
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