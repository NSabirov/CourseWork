package com.sabirov.core_impl.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class GsonUTCDateAdapter : JsonSerializer<Date?>, JsonDeserializer<Date> {
    private val dateFormat: DateFormat

    init {
        dateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) //This is the format I need
        dateFormat.timeZone =
            TimeZone.getTimeZone("UTC")
    }

    @Synchronized
    override fun serialize(
        date: Date?,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(dateFormat.format(date!!))
    }

    @Synchronized
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): Date {
        return try {
            dateFormat.parse(jsonElement.asString) as Date
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }
    }
}