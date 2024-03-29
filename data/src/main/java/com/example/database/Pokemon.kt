package com.example.database

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters(Pokemon.PokemonTypeConverter::class, Pokemon.PokemonRegionConverter::class)
@Entity(tableName = "pokemon_table")
data class Pokemon(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "url")
    val url: String = "",

    @ColumnInfo(name = "height")
    val height: Double = 0.0,

    @ColumnInfo(name = "weight")
    val weight: Double = 0.0,

    @ColumnInfo(name = "types")
    val types: MutableList<PokemonType> = mutableListOf(),

    @ColumnInfo(name = "sprite")
    val sprite: String = "",

    @ColumnInfo(name = "stats")
    val stats: List<Int> = emptyList(),

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "region")
    val region: PokemonRegion = PokemonRegion.UNKNOWN

) {
    class PokemonTypeConverter {
        companion object {
            @JvmStatic
            @TypeConverter
            fun fromListPokemonType(list: MutableList<PokemonType>): String {
                return list.joinToString(",") { it.name }
            }

            @JvmStatic
            @TypeConverter
            fun toListPokemonType(data: String): MutableList<PokemonType> {
                return data.split(",").map {
                    try {
                        PokemonType.valueOf(it)
                    } catch (e: IllegalArgumentException) {
                        Log.e("PokemonTypeConverter", "Error converting $it to PokemonType", e)
                        PokemonType.UNKNOWN
                    }
                }.toMutableList()
            }
        }
    }

    class PokemonRegionConverter {
        companion object {
            @JvmStatic
            @TypeConverter
            fun fromPokemonRegion(region: PokemonRegion): String {
                return region.name
            }

            @JvmStatic
            @TypeConverter
            fun toPokemonRegion(data: String): PokemonRegion {
                return try {
                    PokemonRegion.valueOf(data)
                } catch (e: IllegalArgumentException) {
                    Log.e("PokemonRegionConverter", "Error converting $data to PokemonRegion", e)
                    PokemonRegion.UNKNOWN
                }
            }
        }
    }
}