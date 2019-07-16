package br.com.alissontfb.mypushnotification.data.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import java.io.Serializable
import java.util.*

@RealmClass
open class Task : RealmObject(), Serializable {

    companion object{
        const val ID = "uuid"
        const val DESCRIPTION = "taskDescription"
        const val CREATED = "createdDate"
        const val DONE = "done"
        const val NEW = "new"
        const val EXCLUDED = "excluded"
    }

    @Required
    @PrimaryKey
    @RealmField(name = ID)
    var uuid: String = ""

    @Required
    @RealmField(name = DESCRIPTION)
    var taskDescription: String = ""

    @Required
    @RealmField(name = CREATED)
    var createdDate: Date

    @RealmField(name = DONE)
    var done: Boolean = false

    @RealmField(name = NEW)
    var new: Boolean = true

    @RealmField(name = EXCLUDED)
    var excluded: Boolean = false

    init {
        if (uuid.isEmpty())
            uuid = UUID.randomUUID().toString()

        val cal = Calendar.getInstance()
        createdDate = cal.time
    }
}