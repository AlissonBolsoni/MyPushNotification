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
import kotlin.collections.HashMap

class TaskAdapter(
    private val onClick: (Task) -> Unit,
    private val onLongClick: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private val taskList = HashMap<String,Task>()

    fun setListItems(taskList: ArrayList<Task>){
        this.taskList.clear()
        for (task in taskList){
            this.taskList[task.uuid] = task
        }
        notifyDataSetChanged()
    }

    fun maskRead(uuid: String) {
        val task = this.taskList[uuid]
        if (task != null)
            task.read = true

        notifyDataSetChanged()
    }

    fun addTask(task: Task) {
        this.taskList[task.uuid] = task
        notifyDataSetChanged()
    }

    override fun getItemCount() =this.taskList.size

    fun getItem(position: Int) = this.taskList.values.toList()[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task_layout, parent, false)
        val holder = ViewHolder(view)
        holder.description = view.findViewById(R.id.item_task_description)
        holder.date = view.findViewById(R.id.item_task_created)
        holder.read = view.findViewById(R.id.item_task_read)
        holder.done = view.findViewById(R.id.item_task_done)
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

        if (task.read)
            holder.read.visibility = View.GONE
        else{
            holder.read.visibility = View.VISIBLE
            holder.done.visibility = View.GONE
        }

        if (task.done){
            holder.done.visibility = View.VISIBLE
            holder.read.visibility = View.GONE
        }else
            holder.done.visibility = View.GONE
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var description: TextView
        lateinit var date: TextView
        lateinit var read: ImageView
        lateinit var done: ImageView
        lateinit var card: CardView
    }
}
