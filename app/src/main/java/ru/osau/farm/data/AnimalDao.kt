package ru.osau.farm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals")
    fun getAllAnimals(): List<Animal>

    @Insert
    suspend fun insertAnimals(animals: List<Animal>)

    @Update
    suspend fun updateAnimal(animal: Animal)

    @Query("SELECT COUNT(*) FROM animals")
    suspend fun getDataCount(): Int

    @Query("SELECT * FROM animals WHERE type = :type AND purpose = :purpose")
    suspend fun getAnimalByTypeAndPurpose(type: String, purpose: String): Animal?
}