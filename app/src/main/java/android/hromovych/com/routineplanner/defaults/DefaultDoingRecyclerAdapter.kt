package android.hromovych.com.routineplanner.defaults

import android.annotation.SuppressLint
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.doings.Doing
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView

class DefaultDoingRecyclerAdapter(
    doings: List<Doing>,
    recyclerView: RecyclerView,
    val updateDoing: (Doing) -> Unit,
    val onItemClickAction: (View, Doing) -> Unit
) :
    RecyclerView.Adapter<DefaultDoingRecyclerAdapter.Holder>() {
    var doings = doings
        set(value) {
            field = value
            movedDoings = value.toMutableList()
            notifyDataSetChanged()
        }
        get() = movedDoings.toList()

    private var movedDoings: MutableList<Doing> = doings.toMutableList()

    @SuppressLint("ClickableViewAccessibility")
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.findViewById<TextView>(R.id.doing_title)
        val moveImage = itemView.findViewById<ImageView>(R.id.doing_move_image_view)
        var doing: Doing? = null

        init {
            itemView.findViewById<CheckBox>(R.id.doing_checkbox).visibility = View.GONE
            itemView.setOnClickListener {
                onItemClickAction(it, doing!!)
            }
            moveImage.setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.startDrag(this)
                }

                return@setOnTouchListener true
            }
        }

        fun bind(position: Int) {
            itemView.setBackgroundResource(
                if (position % 2 == 0)
                    R.color.application_background_second
                else
                    R.color.application_background
            )
            doing = movedDoings[position].apply {
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

    override fun getItemCount() = movedDoings.size

    private fun moveItem(from: Int, to: Int) {
        movedDoings.apply {
            val fromDoing = removeAt(from)
            if (to < from) {
                add(to, fromDoing)
            } else {
                add(to - 1, fromDoing)
            }
            updateDoingsPositions(minOf(from, to))
        }
    }

    private fun updateDoingsPositions(startFrom: Int) {
        movedDoings.withIndex().filter { it.index >= startFrom }.forEach {
            val (position, doing) = it

            doing.position = position
            updateDoing(doing)
        }
    }

    private val itemTouchHelper: ItemTouchHelper by lazy {
        val simpleTouchHelper = object : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = recyclerView.adapter as DefaultDoingRecyclerAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                adapter.moveItem(from, to)
                adapter.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //just ignore it
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)

                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }
        }
        ItemTouchHelper(simpleTouchHelper)
    }

    init {
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}