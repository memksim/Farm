package ru.osau.farm.data

/**
 * Класс, описывающий соотношение корма для вида и назначения животного на одну голову
 * @property totalFeed Общее количество съедаемой еды кг/день
 * @property compoundFeed Комбикорм
 * @property grainCost Зерно
 * @property grassCost Трава/сено
 * @property mineralSupplementsCost Минеральные добавки
 */
data class FeedRatio(
    val totalFeed: Double,
    val compoundFeed: Double,
    val grain: Double,
    val grass: Double,
    val mineralSupplements: Double,
)