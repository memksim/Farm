package ru.osau.farm.presentation.input

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.osau.farm.App
import ru.osau.farm.Calculator
import ru.osau.farm.data.Animal
import ru.osau.farm.data.AnimalPurpose
import ru.osau.farm.data.AnimalType
import ru.osau.farm.data.CalculationResult

data class InputIntentState(
    val selectedAnimalType: AnimalType? = null,
    val selectedPurpose: AnimalPurpose? = null,
    val count: String = "",
    val availablePurposes: List<AnimalPurpose> = emptyList(),
    val currentProductivity: String = "",
    val targetProductivity: String = "",
    val monthsToTarget: String = "",
    val calculationResult: CalculationResult? = null
)

class InputViewModel : ViewModel() {
    private val _state = MutableStateFlow(InputIntentState())
    val state: StateFlow<InputIntentState> = _state.asStateFlow()

    private val _animals = MutableStateFlow<MutableList<Animal>>(mutableListOf())
    val animals: StateFlow<List<Animal>> = _animals.asStateFlow()

    init {
        loadAnimals()
    }

    fun resetState() {
        _state.update { InputIntentState() }
    }

    fun onAnimalTypeSelected(type: AnimalType) {
        _state.update { currentState ->
            currentState.copy(
                selectedAnimalType = type,
                selectedPurpose = null,
                availablePurposes = when (type) {
                    AnimalType.CHICKEN -> listOf(
                        AnimalPurpose.ChickenPurpose.EGGS,
                        AnimalPurpose.ChickenPurpose.MEAT
                    )

                    AnimalType.SHEEP -> listOf(
                        AnimalPurpose.SheepPurpose.WOOL,
                        AnimalPurpose.SheepPurpose.MEAT,
                        AnimalPurpose.SheepPurpose.BREEDING
                    )

                    AnimalType.PIG -> listOf(
                        AnimalPurpose.PigPurpose.MEAT,
                        AnimalPurpose.PigPurpose.BREEDING
                    )

                    AnimalType.COW -> listOf(
                        AnimalPurpose.CowPurpose.MILK,
                        AnimalPurpose.CowPurpose.MEAT,
                        AnimalPurpose.CowPurpose.BREEDING
                    )
                }
            )
        }
    }

    fun onPurposeSelected(purpose: AnimalPurpose) {
        _state.update { currentState ->
            currentState.copy(selectedPurpose = purpose)
        }
    }

    fun onCountChanged(count: String) {
        _state.update { currentState ->
            currentState.copy(count = count)
        }
    }

    fun onCurrentProductivityChanged(value: String) {
        _state.update { currentState ->
            currentState.copy(currentProductivity = value)
        }
    }

    fun onTargetProductivityChanged(value: String) {
        _state.update { currentState ->
            currentState.copy(targetProductivity = value)
        }
    }

    fun onMonthsToTargetChanged(value: String) {
        _state.update { currentState ->
            currentState.copy(monthsToTarget = value)
        }
    }

    fun loadAnimals() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _animals.update { App.animalRepository.getAllAnimals().toMutableList() }
            }
        }
    }

    fun updateAnimal(animal: Animal) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                App.animalRepository.updateAnimal(animal)
                _animals.update { currentAnimals ->
                    currentAnimals.map { existingAnimal ->
                        if (existingAnimal.id == animal.id) animal else existingAnimal
                    }.toMutableList()
                }
            }
        }
    }

    fun calculate() {
        viewModelScope.launch {
            val animal = App.animalRepository.getAnimalByTypeAndPurpose(
                state.value.selectedAnimalType,
                state.value.selectedPurpose
            ) ?: return@launch

            val result = Calculator.calculate(
                animal = animal,
                headcount = state.value.count.toIntOrNull() ?: 1,
                currentProductivity = state.value.currentProductivity.toDoubleOrNull() ?: 0.0,
                targetProductivity = state.value.targetProductivity.toDoubleOrNull() ?: 1.0,
                months = state.value.monthsToTarget.toIntOrNull() ?: 12
            )

            _state.update {
                it.copy(calculationResult = result)
            }
        }
    }
}