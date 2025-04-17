package ru.osau.farm

import ru.osau.farm.data.Animal
import ru.osau.farm.data.CalculationResult
import ru.osau.farm.data.recommendationsByAnimal

object Calculator {
    fun calculate(
        animal: Animal,
        headcount: Int,
        currentProductivity: Double,
        targetProductivity: Double,
        months: Int,
    ): CalculationResult {
        val feedRatio = animal.purpose.ratio

        val productivityRatio = if (currentProductivity > 0)
            targetProductivity / currentProductivity
        else 1.0

        val adjustmentFactor = productivityRatio * animal.purpose.k
        val totalDays = months * 30
        val totalFeedKg = feedRatio.totalFeed * headcount * totalDays * adjustmentFactor

        val compoundFeedKg = totalFeedKg * feedRatio.compoundFeed
        val grainKg = totalFeedKg * feedRatio.grain
        val grassKg = totalFeedKg * feedRatio.grass
        val supplementsKg = totalFeedKg * feedRatio.mineralSupplements

        val compoundCost = compoundFeedKg * animal.compoundFeedCost
        val grainCost = grainKg * animal.grainCost
        val grassCost = grassKg * animal.grassCost
        val supplementsCost = supplementsKg * animal.mineralSupplementsCost

        val totalCost = compoundCost + grainCost + grassCost + supplementsCost
        val costPerHead = totalCost / headcount

        val feedBreakdownPerHead = mapOf(
            "Комбикорм" to compoundCost / headcount,
            "Зерно" to grainCost / headcount,
            "Трава/Сено" to grassCost / headcount,
            "Минеральные добавки" to supplementsCost / headcount
        )

        val recommendations =
            recommendationsByAnimal[animal.type]?.get(animal.purpose) ?: emptyList()

        return CalculationResult(
            animal = animal,
            calculatedPrice = totalCost,
            costPerHead = costPerHead,
            feedBreakdownPerHead = feedBreakdownPerHead,
            recommendations = recommendations
        )
    }
}