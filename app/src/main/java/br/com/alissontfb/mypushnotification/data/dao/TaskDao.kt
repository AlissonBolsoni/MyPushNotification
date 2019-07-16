package br.com.alissontfb.mypushnotification.data.dao

import br.com.alissontfb.mypushnotification.data.entity.Task
import io.realm.Realm
import io.realm.RealmResults

open class TaskDao {
    private val _class = Task::class.java
    private val realm: Realm = Realm.getDefaultInstance()

    fun getAll(callback: (List<Task>) -> Unit) {
        try {
            val result = realm.where(_class).and().equalTo(Task.EXCLUDED, false).findAllAsync()
            result.addChangeListener { r: RealmResults<Task> ->
                callback(r.toList())
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun createTask(taskName: String) {
        val task = Task()
        task.taskDescription = taskName
        realm.executeTransactionAsync {
            it.insertOrUpdate(task)
        }
    }

    fun editTaskName(id: String, taskName: String, callback: () -> Unit) {
        realm.executeTransactionAsync {
            val task = it.where(_class).equalTo("id", id).findFirst()
            task!!.taskDescription = taskName
            callback()
        }
    }

    fun updateTask(id: String, action: TaskUpdateTypes, callback: () -> Unit) {
        realm.executeTransactionAsync {
            val task = it.where(_class).equalTo("id", id).findFirst()
            when(action){
                TaskUpdateTypes.READ ->
                    task!!.new = false
                TaskUpdateTypes.FINISH ->
                    task!!.done = true
                else ->
                    task!!.excluded = true
            }

            callback()
        }
    }
}
