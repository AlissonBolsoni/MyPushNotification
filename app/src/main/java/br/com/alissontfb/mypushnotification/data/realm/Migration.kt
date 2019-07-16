package br.com.alissontfb.mypushnotification.data.realm

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var oldVersion = oldVersion
        val schema = realm.schema

        if (oldVersion == 0L) {
            //            RealmObjectSchema taskSchema = schema.get("Task");
            //
            //            taskSchema.addField("timestamp", long.class)
            //                    .transform(obj -> obj.set("timestamp", 0));

            oldVersion++
        }
    }
}
