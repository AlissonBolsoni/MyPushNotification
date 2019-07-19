package br.com.alissontfb.mypushnotification.data.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import java.io.Serializable
import java.util.*

open class Task : RealmObject() {

    companion object{
        const val ID = "uuid"
        const val DESCRIPTION = "taskDescription"
        const val CREATED = "createdDate"
        const val DONE = "done"
        const val NEW = "read"
        const val EXCLUDED = "excluded"
    }

    @Required
    @PrimaryKey
    var uuid: String = ""

    @Required
    var taskDescription: String = ""

    @Required
    var createdDate: Date

    var done: Boolean = false

    var read: Boolean = false

    var excluded: Boolean = false

    init {
        if (uuid.isEmpty())
            uuid = UUID.randomUUID().toString()

        val cal = Calendar.getInstance()
        createdDate = cal.time
    }
}