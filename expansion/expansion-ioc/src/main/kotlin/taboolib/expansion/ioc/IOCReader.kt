package taboolib.expansion.ioc

import taboolib.common.LifeCycleimport taboolib.common.env.RuntimeDependenciesimport taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Awake
import taboolib.common.platform.Schedule
import taboolib.expansion.ioc.annotation.Autowired
import taboolib.expansion.ioc.database.IOCDatabase
import taboolib.expansion.ioc.database.impl.IOCDatabaseYaml
import taboolib.expansion.ioc.event.FieldReadEvent
import taboolib.expansion.ioc.typeread.TypeReadManager
import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap

@RuntimeDependencies(
    RuntimeDependency(value = "!com.google.code.gson:gson:2.8.7", test = "!com.google.gson.JsonElement")
)
object IOCReader {

    private val database = ConcurrentHashMap<String, IOCDatabase>()
    private val fields = ConcurrentHashMap<Field, Pair<IOCDatabase, Class<*>>>()

    fun readRegister(classes: List<Class<*>>, defaultIOCDatabase: IOCDatabase = IOCDatabaseYaml()) {
        classes.forEach runClass@{ classze: Class<*> ->
            classze.declaredFields.forEach { field ->
                if (!field.isAnnotationPresent(Autowired::class.java)) {
                    return@forEach
                }
                field.isAccessible = true
                val event = FieldReadEvent(classze, field, defaultIOCDatabase)
                event.call()
                if (event.isCancelled) {
                    return@forEach
                }
                val database = this.database.getOrPut(field.toString()) { event.iocDatabase.init(classze, field.name) }
                println(classze)
                TypeReadManager.getReader(field.type).readAll(classze, field, database)
                fields[field] = database to classze
            }
        }
    }


    @Schedule(period = 2400, async = true)
    @Awake(LifeCycle.DISABLE)
    fun write() {
        fields.forEach { t, u ->
            t.isAccessible = true
            TypeReadManager.getReader(t.type).writeAll(t, u.second, u.first)
        }
    }

}