package ru.osau.farm

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.osau.farm.data.Animal
import ru.osau.farm.data.AnimalPurpose
import ru.osau.farm.data.AnimalRepository
import ru.osau.farm.data.AnimalType
import ru.osau.farm.data.AppDatabase

private val defaultAnimals = listOf(
    Animal(
        type = AnimalType.CHICKEN,
        purpose = AnimalPurpose.ChickenPurpose.EGGS,
        compoundFeedCost = 50.0,
        grainCost = 30.0,
        grassCost = 10.0,
        mineralSupplementsCost = 5.0
    ),
    Animal(
        type = AnimalType.CHICKEN,
        purpose = AnimalPurpose.ChickenPurpose.MEAT,
        compoundFeedCost = 60.0,
        grainCost = 35.0,
        grassCost = 15.0,
        mineralSupplementsCost = 7.0
    ),
    Animal(
        type = AnimalType.SHEEP,
        purpose = AnimalPurpose.SheepPurpose.WOOL,
        compoundFeedCost = 100.0,
        grainCost = 50.0,
        grassCost = 30.0,
        mineralSupplementsCost = 10.0
    ),
    Animal(
        type = AnimalType.SHEEP,
        purpose = AnimalPurpose.SheepPurpose.MEAT,
        compoundFeedCost = 120.0,
        grainCost = 60.0,
        grassCost = 40.0,
        mineralSupplementsCost = 15.0
    ),
    Animal(
        type = AnimalType.SHEEP,
        purpose = AnimalPurpose.SheepPurpose.BREEDING,
        compoundFeedCost = 150.0,
        grainCost = 70.0,
        grassCost = 50.0,
        mineralSupplementsCost = 20.0
    ),
    Animal(
        type = AnimalType.PIG,
        purpose = AnimalPurpose.PigPurpose.MEAT,
        compoundFeedCost = 200.0,
        grainCost = 100.0,
        grassCost = 30.0,
        mineralSupplementsCost = 25.0
    ),
    Animal(
        type = AnimalType.PIG,
        purpose = AnimalPurpose.PigPurpose.BREEDING,
        compoundFeedCost = 250.0,
        grainCost = 120.0,
        grassCost = 40.0,
        mineralSupplementsCost = 30.0
    ),
    Animal(
        type = AnimalType.COW,
        purpose = AnimalPurpose.CowPurpose.MILK,
        compoundFeedCost = 300.0,
        grainCost = 150.0,
        grassCost = 100.0,
        mineralSupplementsCost = 40.0
    ),
    Animal(
        type = AnimalType.COW,
        purpose = AnimalPurpose.CowPurpose.MEAT,
        compoundFeedCost = 350.0,
        grainCost = 180.0,
        grassCost = 120.0,
        mineralSupplementsCost = 45.0
    ),
    Animal(
        type = AnimalType.COW,
        purpose = AnimalPurpose.CowPurpose.BREEDING,
        compoundFeedCost = 400.0,
        grainCost = 200.0,
        grassCost = 150.0,
        mineralSupplementsCost = 50.0
    )
)

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
        animalRepository = AnimalRepository(database.animalDao())

        CoroutineScope(Dispatchers.IO).launch {
            if (animalRepository.checkIsDatabaseEmpty()) {
                animalRepository.insertAnimals(defaultAnimals)
            }
        }
    }

    companion object {
        lateinit var database: AppDatabase
        lateinit var animalRepository: AnimalRepository
    }
} 