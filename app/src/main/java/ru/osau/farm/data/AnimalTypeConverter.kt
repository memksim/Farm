package ru.osau.farm.data

import androidx.room.TypeConverter

class AnimalTypeConverter {
    @TypeConverter
    fun fromAnimalType(type: AnimalType): String {
        return type.name
    }

    @TypeConverter
    fun toAnimalType(type: String): AnimalType {
        return AnimalType.valueOf(type)
    }
} 