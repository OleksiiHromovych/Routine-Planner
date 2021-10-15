package android.hromovych.com.routineplanner.basic

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hromovych.domain.utils.EqualsCheck
import com.hromovych.domain.utils.Positionable

@Suppress("UNCHECKED_CAST")
fun <T> getItemTouchHelper(
    updateAfterMoved: (List<T>) -> Unit,
): ItemTouchHelper where T : EqualsCheck<T>, T : Positionable =
    ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

            private var dragFrom = -1
            private var dragTo = -1

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                val adapter = recyclerView.adapter as BasicAdapter<*, T>
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                if (dragFrom == -1) {
                    dragFrom = from
                }
                dragTo = to

                val list = adapter.currentList.toMutableList()

                list.moveItem(from, to)

                adapter.submitList(list)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // ignoring it
            }

            override fun onSelectedChanged(
                viewHolder: RecyclerView.ViewHolder?,
                actionState: Int,
            ) {
                super.onSelectedChanged(viewHolder, actionState)

                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f

                val adapter = recyclerView.adapter as BasicAdapter<*, T>

                if (dragFrom != -1 && dragTo != -1 && dragFrom != dragTo) {
                    val updatedList = adapter.currentList.subList(minOf(dragFrom, dragTo),
                        maxOf(dragFrom, dragTo) + 1)
                    updateAfterMoved(updatedList)
                }
                dragFrom = -1
                dragTo = -1

            }

            private fun MutableList<T>.moveItem(from: Int, to: Int) {
                val fromDoing = removeAt(from)
                add(to, fromDoing)
                updatePositions(this, from, to)
//                if (to < from) {
//                    add(to, fromDoing)
//                } else {
//                    add(to -1, fromDoing)
//                }
                //                list[from].position =
//                    list[to].position.also { list[to].position = list[from].position }
//                list[from] = list[to].also { list[to] = list[from] }

            }

            private fun updatePositions(list: List<T>, from: Int, to: Int) {
                val range = if (to < from) to..from else from..to
                range.forEach {
                    list[it].position = it
                }
            }
        }
    )