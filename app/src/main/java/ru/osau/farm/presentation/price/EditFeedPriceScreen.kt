package ru.osau.farm.presentation.price

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.osau.farm.presentation.input.InputViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFeedPriceScreen(
    viewModel: InputViewModel,
    navigateBack: () -> Unit,
) {
    val animals by viewModel.animals.collectAsState()
    val pagerState = rememberPagerState(pageCount = { animals.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактирование стоимости кормов") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) { page ->
                val animal = animals[page]
                var isEditing by remember { mutableStateOf(false) }
                var compoundFeedCost by remember { mutableStateOf(animal.compoundFeedCost.toString()) }
                var grainCost by remember { mutableStateOf(animal.grainCost.toString()) }
                var grassCost by remember { mutableStateOf(animal.grassCost.toString()) }
                var mineralSupplementsCost by remember { mutableStateOf(animal.mineralSupplementsCost.toString()) }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Эмодзи и назначение
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = when (animal.type) {
                                ru.osau.farm.data.AnimalType.CHICKEN -> "🐔"
                                ru.osau.farm.data.AnimalType.SHEEP -> "🐑"
                                ru.osau.farm.data.AnimalType.PIG -> "🐖"
                                ru.osau.farm.data.AnimalType.COW -> "🐄"
                            },
                            fontSize = 48.sp
                        )
                    }
                    Text(
                        text = animal.purpose.localized,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Комбикорм
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEditing) {
                            OutlinedTextField(
                                value = compoundFeedCost,
                                onValueChange = { compoundFeedCost = it },
                                label = { Text("Комбикорм (руб/кг)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Text(
                                text = "Комбикорм: ${
                                    String.format(
                                        "%.2f",
                                        animal.compoundFeedCost
                                    )
                                } руб/кг",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        IconButton(
                            onClick = {
                                if (isEditing) {
                                    val updatedAnimal = animal.copy(
                                        compoundFeedCost = compoundFeedCost.toDoubleOrNull()
                                            ?: animal.compoundFeedCost,
                                        grainCost = grainCost.toDoubleOrNull() ?: animal.grainCost,
                                        grassCost = grassCost.toDoubleOrNull() ?: animal.grassCost,
                                        mineralSupplementsCost = mineralSupplementsCost.toDoubleOrNull()
                                            ?: animal.mineralSupplementsCost
                                    )
                                    Log.d("VKT", "prev: $animal")
                                    Log.d("VKT", "prev: $updatedAnimal")
                                    viewModel.updateAnimal(updatedAnimal)
                                }
                                isEditing = !isEditing
                            }
                        ) {
                            Icon(
                                imageVector = if (isEditing) Icons.Default.Done else Icons.Default.Edit,
                                contentDescription = if (isEditing) "Сохранить" else "Редактировать"
                            )
                        }
                    }

                    // Зерно
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEditing) {
                            OutlinedTextField(
                                value = grainCost,
                                onValueChange = { grainCost = it },
                                label = { Text("Зерно (руб/кг)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Text(
                                text = "Зерно: ${String.format("%.2f", animal.grainCost)} руб/кг",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Трава/Сено
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEditing) {
                            OutlinedTextField(
                                value = grassCost,
                                onValueChange = { grassCost = it },
                                label = { Text("Трава/Сено (руб/кг)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Text(
                                text = "Трава/Сено: ${
                                    String.format(
                                        "%.2f",
                                        animal.grassCost
                                    )
                                } руб/кг",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Минеральные добавки
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEditing) {
                            OutlinedTextField(
                                value = mineralSupplementsCost,
                                onValueChange = { mineralSupplementsCost = it },
                                label = { Text("Минеральные добавки (руб/кг)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Text(
                                text = "Минеральные добавки: ${
                                    String.format(
                                        "%.2f",
                                        animal.mineralSupplementsCost
                                    )
                                } руб/кг",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Индикатор точек
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)
                    )
                }
            }

        }
    }
}