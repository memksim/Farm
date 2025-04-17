package ru.osau.farm.data

data class CalculationResult(
    val animal: Animal,
    val calculatedPrice: Double,
    val costPerHead: Double,
    val feedBreakdownPerHead: Map<String, Double>,
    val recommendations: List<String>
)