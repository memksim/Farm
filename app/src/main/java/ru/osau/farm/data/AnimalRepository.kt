package ru.osau.farm.data

import android.util.Log

class AnimalRepository(private val animalDao: AnimalDao) {

    suspend fun checkIsDatabaseEmpty(): Boolean {
        return animalDao.getDataCount() == 0
    }

    suspend fun getAllAnimals(): List<Animal> {
        return animalDao.getAllAnimals()
    }

    suspend fun insertAnimals(animals: List<Animal>) {
        animalDao.insertAnimals(animals)
    }

    suspend fun updateAnimal(animal: Animal) {
        animalDao.updateAnimal(animal)
        Log.d("VKT", "updateAnimal: $animal")
    }

    suspend fun getAnimalByTypeAndPurpose(type: AnimalType?, purpose: AnimalPurpose?): Animal? {
        val convertedType = AnimalTypeConverter().fromAnimalType(type ?: return null)
        val convertedPurpose = AnimalPurposeConverter().fromAnimalPurpose(purpose ?: return null)
        return animalDao.getAnimalByTypeAndPurpose(convertedType, convertedPurpose)
    }

} 