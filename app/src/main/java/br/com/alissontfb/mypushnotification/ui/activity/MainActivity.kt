package br.com.alissontfb.mypushnotification.ui.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import br.com.alissontfb.mypushnotification.R
import br.com.alissontfb.mypushnotification.data.dao.TaskDao
import br.com.alissontfb.mypushnotification.data.dao.TaskUpdateTypes
import br.com.alissontfb.mypushnotification.data.entity.Task
import br.com.alissontfb.mypushnotification.ui.adapter.TaskAdapter
import br.com.alissontfb.mypushnotification.ui.callback.TaskItemCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dao = TaskDao()
    private var dialog: AlertDialog? = null
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent.extras != null) {
            for (key in intent.extras.keySet()) {
                if (key == "message") {
                    val message = intent.extras.getString(key)
                    if (message != null && message.isNotEmpty())
                        TaskDao().createTask(message)
                }
            }
        }

        main_tasks_fab_add.setOnClickListener {
            createTaskDialog()
        }

    }

    private fun createTaskDialog(task: Task? = null) {
        val editTask = task != null
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.app_name) + if (editTask) " - Editar" else " - Criar")
        builder.setMessage(getString(R.string.create_task_message))
        val edText = EditText(this)
        builder.setView(edText)
        builder.setPositiveButton("OK") { _, _ ->
            val text = edText.text.toString()
            if (text.isNotEmpty()) {
                if (editTask) {
                    dao.updateTask(task!!.uuid, TaskUpdateTypes.DESCRIPTION, {
                        startTaskList()
                    }, text)
                } else {
                    val task = dao.createTask(text)
                    adapter.addTask(task)
                }
            } else
                Toast.makeText(this, "Não pode criar uma tarefa sem descrição!", Toast.LENGTH_SHORT).show()
        }
        dialog = builder.create()
        dialog?.show()
    }

    override fun onResume() {
        super.onResume()
        startTaskList()
        val itemTouchHelper = ItemTouchHelper(TaskItemCallback(adapter) {
            startTaskList()
        })
        itemTouchHelper.attachToRecyclerView(main_tasks_list)
    }

    override fun onPause() {
        super.onPause()

        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    private fun startTaskList() {
        val taskList = ArrayList<Task>()

        try {
            taskList.addAll(dao.getAll())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        adapter = TaskAdapter({ task ->
            dao.updateTask(task.uuid, TaskUpdateTypes.READ, {
                this.adapter.maskRead(task.uuid)
            })
        }, { task ->
            createTaskDialog(task)
        })
        adapter.setListItems(ArrayList(taskList))
        main_tasks_list.adapter = adapter
    }

}
