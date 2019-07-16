package br.com.alissontfb.mypushnotification.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.alissontfb.mypushnotification.R
import br.com.alissontfb.mypushnotification.data.entity.Task
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TaskAdapter(
    private val onClick: (Task) -> Unit,
    private val onLongClick: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private val taskList = ArrayList<Task>()

    fun setListItens(taskList: ArrayList<Task>){
        this.taskList.clear()
        this.taskList.addAll(taskList)
    }

    override fun getItemCount() =this.taskList.size

    private fun getItem(position: Int) = this.taskList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task_layout, parent, false)
        val holder = ViewHolder(view)
        holder.description = view.findViewById(R.id.item_task_description)
        holder.date = view.findViewById(R.id.item_task_created)
        holder.new = view.findViewById(R.id.item_task_new)
        holder.card = view.findViewById(R.id.item_task_card)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        holder.card.setOnClickListener { onClick(task) }
        holder.card.setOnLongClickListener {
            onLongClick(task)
            true
        }

        holder.description.text = task.taskDescription

        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.createdDate)
        holder.date.text = "Criado em: $date"

        if (task.new)
            holder.new.visibility = View.VISIBLE
        else
            holder.new.visibility = View.GONE
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var description: TextView
        lateinit var date: TextView
        lateinit var new: ImageView
        lateinit var card: CardView
    }
}
