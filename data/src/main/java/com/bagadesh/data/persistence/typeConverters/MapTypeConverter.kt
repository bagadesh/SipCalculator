package com.bagadesh.data.persistence.typeConverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * Created by bagadesh on 02/09/22.
 */
@ProvidedTypeConverter
class MapTypeConverter @Inject constructor(private val gson: Gson) {

    @TypeConverter
    fun mapToString(value: Map<String, Any>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun stringToMap(value: String): Map<String, Any> {
        return gson.fromJson(value, object : TypeToken<Map<String, Any>>() {}.type)
    }

}