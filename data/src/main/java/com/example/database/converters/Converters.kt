package com.example.database.converters

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromListInt(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListInt(data: String): List<Int> {
        return listOf(*data.split(",").map { if (it.isNotEmpty()) it.toInt() else 0 }
            .toTypedArray())
    }
}
