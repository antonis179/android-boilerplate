package org.amoustakos.utils.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Modifier
import java.util.*


object RequestUtil {


    /**
     * Converts the provided class into a network query map
     * (helpful for GET requests).
     *
     * CAUTION! Uses reflection. The fields you want to include
     * must be annotated with [@Expose].
     *
     * Supported field types:
     *
     * 1. [String]
     * 2. [Int]
     * 3. [Long]
     * 4. [Double]
     * 5. [Float]
     * 6. [Enum] (by using toString method)
     */
    @JvmStatic
    fun asQueryMap(cl: Any): Map<String, String> {
        val mapping = HashMap<String, String>()
        val fields = cl.javaClass.declaredFields
        val fieldsList = ArrayList(Arrays.asList(*fields))
        var current: Class<in RequestUtil>? = cl.javaClass

        // Loop through all inheritance levels and get fields
        while (current?.superclass != RequestUtil::class.java) {
            current = current?.superclass
            fieldsList.addAll(Arrays.asList(*current?.declaredFields))
        }

        for (f in fieldsList) {
            if (Modifier.isTransient(f.modifiers))
                continue
            if (f.getAnnotation(Expose::class.java) == null)
                continue

            val name: String
            val serializedName = f.getAnnotation(SerializedName::class.java)

            name = serializedName?.value ?: f.name

            f.isAccessible = true
            try {
                val o = f.get(this)
                getValue(o)?. let {
                    mapping[name] = it
                } ?: mapping.remove(name)
            } catch (ignored: IllegalArgumentException) {}
            catch (ignored: IllegalAccessException) {}
        }

        return mapping
    }


    @Throws(IllegalArgumentException::class)
    private fun getValue(o: Any?): String? {
        if (o == null)
            return null

        return o as? String ?: if (
		        o is Int
                || o is Long
                || o is Double
                || o is Float) {
            o.toString()
        }
        else (o as? Enum<*>)?.toString()
                ?: ((o as? Boolean)?.toString()
                ?: throw IllegalArgumentException("Illegal object type"))
    }

}
