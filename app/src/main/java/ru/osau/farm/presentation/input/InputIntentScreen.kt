package ru.osau.farm.presentation.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.osau.farm.data.AnimalPurpose
import ru.osau.farm.data.AnimalType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputIntentScreen(
    viewModel: InputViewModel,
    onNavigateToTarget: () -> Unit
) {
    var expandedAnimalType by remember { mutableStateOf(false) }
    var expandedPurpose by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ввод данных") },
                actions = {
                    IconButton(onClick = { /* TODO: Open settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Выбор типа животного
            ExposedDropdownMenuBox(
                expanded = expandedAnimalType,
                onExpandedChange = { expandedAnimalType = it }
            ) {
                OutlinedTextField(
                    value = state.selectedAnimalType?.localized ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Вид животного") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAnimalType) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedAnimalType,
                    onDismissRequest = { expandedAnimalType = false }
                ) {
                    AnimalType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.localized) },
                            onClick = {
                                viewModel.onAnimalTypeSelected(type)
                                expandedAnimalType = false
                            }
                        )
                    }
                }
            }

            if (state.selectedAnimalType != null) {
                // Выбор назначения
                ExposedDropdownMenuBox(
                    expanded = expandedPurpose,
                    onExpandedChange = { expandedPurpose = it }
                ) {
                    OutlinedTextField(
                        value = state.selectedPurpose?.let { purpose ->
                            when (purpose) {
                                is AnimalPurpose.ChickenPurpose.EGGS -> "Яйца"
                                is AnimalPurpose.ChickenPurpose.MEAT -> "Мясо"
                                is AnimalPurpose.SheepPurpose.WOOL -> "Шерсть"
                                is AnimalPurpose.SheepPurpose.MEAT -> "Мясо"
                                is AnimalPurpose.SheepPurpose.BREEDING -> "Разведение"
                                is AnimalPurpose.PigPurpose.MEAT -> "Мясо"
                                is AnimalPurpose.PigPurpose.BREEDING -> "Разведение"
                                is AnimalPurpose.CowPurpose.MILK -> "Молоко"
                                is AnimalPurpose.CowPurpose.MEAT -> "Мясо"
                                is AnimalPurpose.CowPurpose.BREEDING -> "Разведение"
                            }
                        } ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Назначение") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPurpose) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedPurpose,
                        onDismissRequest = { expandedPurpose = false }
                    ) {
                        state.availablePurposes.forEach { purpose ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        when (purpose) {
                                            is AnimalPurpose.ChickenPurpose.EGGS -> "Яйца"
                                            is AnimalPurpose.ChickenPurpose.MEAT -> "Мясо"
                                            is AnimalPurpose.SheepPurpose.WOOL -> "Шерсть"
                                            is AnimalPurpose.SheepPurpose.MEAT -> "Мясо"
                                            is AnimalPurpose.SheepPurpose.BREEDING -> "Разведение"
                                            is AnimalPurpose.PigPurpose.MEAT -> "Мясо"
                                            is AnimalPurpose.PigPurpose.BREEDING -> "Разведение"
                                            is AnimalPurpose.CowPurpose.MILK -> "Молоко"
                                            is AnimalPurpose.CowPurpose.MEAT -> "Мясо"
                                            is AnimalPurpose.CowPurpose.BREEDING -> "Разведение"
                                        }
                                    )
                                },
                                onClick = {
                                    viewModel.onPurposeSelected(purpose)
                                    expandedPurpose = false
                                }
                            )
                        }
                    }
                }
            }

            // Поле ввода количества
            OutlinedTextField(
                value = state.count,
                onValueChange = { viewModel.onCountChanged(it) },
                label = { Text("Количество голов") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            if (state.selectedAnimalType != null && state.selectedPurpose != null && state.count.isNotEmpty()) {// Кнопка далее
                Button(
                    onClick = onNavigateToTarget,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Далее")
                }
            }
        }
    }
}