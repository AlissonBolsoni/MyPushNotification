package br.com.alissontfb.mypushnotification.data.dao

import br.com.alissontfb.mypushnotification.data.entity.Task
import io.realm.Realm
import io.realm.RealmResults

open class TaskDao {
    private val _class = Task::class.java
    private val realm: Realm = Realm.getDefaultInstance()

    fun getAll(): List<Task> {
        try {
            return realm.where(_class).and().equalTo(Task.EXCLUDED, false).findAll().toList()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    fun createTask(taskName: String):Task {
        val task = Task()
        task.taskDescription = taskName
        realm.executeTransaction {
            it.insertOrUpdate(task)
        }
        return task
    }

//    fun editTaskName(id: String, taskName: String, callback: () -> Unit) {
//        realm.executeTransactionAsync {
//            val task = it.where(_class).equalTo(Task.ID, id).findFirst()
//            task!!.taskDescription = taskName
//            task!!.read = false
//            it.insertOrUpdate(task)
//            callback()
//        }
//    }

    fun updateTask(id: String, action: TaskUpdateTypes, callback: () -> Unit, description: String = "") {
        realm.executeTransaction { realm ->
            val task = realm.where(Task::class.java).equalTo(Task.ID, id).findFirst()
            if (task != null) {
                when (action) {
                    TaskUpdateTypes.READ -> {
                        if (!task.read) {
                            task.read = !task.read
                        }
                    }
                    TaskUpdateTypes.FINISH -> {
                        if (!task.done)
                            task.done = !task.done
                    }
                    TaskUpdateTypes.DESCRIPTION -> {
                        task.taskDescription = description
                        if (!task.read)
                            task.read = !task.read
                    }
                    else -> {
                        if (!task.excluded)
                            task.excluded = !task.excluded
                    }
                }
            }
            callback()
        }

    }
}
