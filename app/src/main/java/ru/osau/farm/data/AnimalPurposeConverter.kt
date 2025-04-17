package ru.osau.farm.data

import androidx.room.TypeConverter

class AnimalPurposeConverter {
    @TypeConverter
    fun fromAnimalPurpose(purpose: AnimalPurpose): String {
        return when (purpose) {
            is AnimalPurpose.ChickenPurpose.EGGS -> "CHICKEN_EGGS"
            is AnimalPurpose.ChickenPurpose.MEAT -> "CHICKEN_MEAT"
            is AnimalPurpose.SheepPurpose.WOOL -> "SHEEP_WOOL"
            is AnimalPurpose.SheepPurpose.MEAT -> "SHEEP_MEAT"
            is AnimalPurpose.SheepPurpose.BREEDING -> "SHEEP_BREEDING"
            is AnimalPurpose.PigPurpose.MEAT -> "PIG_MEAT"
            is AnimalPurpose.PigPurpose.BREEDING -> "PIG_BREEDING"
            is AnimalPurpose.CowPurpose.MILK -> "COW_MILK"
            is AnimalPurpose.CowPurpose.MEAT -> "COW_MEAT"
            is AnimalPurpose.CowPurpose.BREEDING -> "COW_BREEDING"
        }
    }

    @TypeConverter
    fun toAnimalPurpose(purpose: String): AnimalPurpose {
        return when (purpose) {
            "CHICKEN_EGGS" -> AnimalPurpose.ChickenPurpose.EGGS
            "CHICKEN_MEAT" -> AnimalPurpose.ChickenPurpose.MEAT
            "SHEEP_WOOL" -> AnimalPurpose.SheepPurpose.WOOL
            "SHEEP_MEAT" -> AnimalPurpose.SheepPurpose.MEAT
            "SHEEP_BREEDING" -> AnimalPurpose.SheepPurpose.BREEDING
            "PIG_MEAT" -> AnimalPurpose.PigPurpose.MEAT
            "PIG_BREEDING" -> AnimalPurpose.PigPurpose.BREEDING
            "COW_MILK" -> AnimalPurpose.CowPurpose.MILK
            "COW_MEAT" -> AnimalPurpose.CowPurpose.MEAT
            "COW_BREEDING" -> AnimalPurpose.CowPurpose.BREEDING
            else -> throw IllegalArgumentException("Unknown purpose: $purpose")
        }
    }
} 