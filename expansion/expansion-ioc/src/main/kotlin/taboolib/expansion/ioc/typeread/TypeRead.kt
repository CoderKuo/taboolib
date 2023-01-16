package taboolib.expansion.ioc.typeread

import taboolib.expansion.ioc.database.IOCDatabase
import java.lang.reflect.Field

interface TypeRead {

    val type: Class<*>

    fun readAll(clazz: Class<*>, field: Field, database: IOCDatabase): Any

    fun writeAll(field: Field, source: Class<*>, database: IOCDatabase)

}