package ru.osau.farm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * Класс, представляющий животное в базе данных
 * @property id Уникальный идентификатор животного
 * @property type Тип животного (например, корова, свинья и т.д.)
 * @property purpose Назначение животного (например, мясное, молочное и т.д.)
 * @property compoundFeedCost Стоимость комбикорма для животного (руб/кг)
 * @property grainCost Стоимость зерна для животного (руб/кг)
 * @property grassCost Стоимость травы/сена для животного (руб/кг)
 * @property mineralSupplementsCost Стоимость минеральных добавок для животного (руб/кг)
 */
@Entity(tableName = "animals")
@TypeConverters(AnimalTypeConverter::class, AnimalPurposeConverter::class)
data class Animal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: AnimalType,
    val purpose: AnimalPurpose,
    val compoundFeedCost: Double,
    val grainCost: Double,
    val grassCost: Double,
    val mineralSupplementsCost: Double,
) 