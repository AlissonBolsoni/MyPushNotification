package br.com.alissontfb.mypushnotification.ui.callback

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.alissontfb.mypushnotification.data.dao.TaskDao
import br.com.alissontfb.mypushnotification.data.dao.TaskUpdateTypes
import br.com.alissontfb.mypushnotification.ui.adapter.TaskAdapter

class TaskItemCallback(private val adapter: TaskAdapter, private val callback: () -> Unit) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val slice = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        return makeMovementFlags(0, slice)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        if (ItemTouchHelper.LEFT == direction) {
            TaskDao().updateTask(adapter.getItem(position).uuid, TaskUpdateTypes.DELETE, {callback()})
        } else {
            TaskDao().updateTask(adapter.getItem(position).uuid, TaskUpdateTypes.FINISH, {callback()})
        }
    }
}