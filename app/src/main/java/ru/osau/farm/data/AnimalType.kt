package ru.osau.farm.data

enum class AnimalType(val localized: String) {
    CHICKEN("Курицы"),
    SHEEP("Овцы"),
    PIG("Свиньи"),
    COW("Коровы")
}

/**
 * @property k - коэффициент продуктивности
 * @property ratio - соотношение корма
 * */
sealed class AnimalPurpose(
    val k: Double,
    val ratio: FeedRatio,
    val localized: String
) {
    abstract val type: AnimalType

    sealed class ChickenPurpose(
        k: Double,
        ratio: FeedRatio,
        localized: String
    ) : AnimalPurpose(k, ratio, localized) {
        override val type: AnimalType = AnimalType.CHICKEN

        object EGGS : ChickenPurpose(
            k = 1.3,
            ratio = FeedRatio(0.13, 0.6, 0.2, 0.05, 0.15),
            localized = "Яйца"
        )

        object MEAT : ChickenPurpose(
            k = 1.0,
            ratio = FeedRatio(0.15, 0.5, 0.3, 0.1, 0.1),
            localized = "Мясо"
        )
    }

    sealed class SheepPurpose(
        k: Double,
        ratio: FeedRatio,
        localized: String
    ) : AnimalPurpose(k, ratio, localized) {
        override val type: AnimalType = AnimalType.SHEEP

        object WOOL : SheepPurpose(
            k = 1.1,
            ratio = FeedRatio(2.2, 0.3, 0.2, 0.4, 0.1),
            localized = "Шерсть"
        )

        object MEAT : SheepPurpose(
            k = 1.0,
            ratio = FeedRatio(2.5, 0.4, 0.3, 0.2, 0.1),
            localized = "Мясо"
        )

        object BREEDING : SheepPurpose(
            k = 1.25,
            ratio = FeedRatio(2.4, 0.35, 0.25, 0.3, 0.1),
            localized = "Разведение"
        )
    }

    sealed class PigPurpose(
        k: Double,
        ratio: FeedRatio,
        localized: String
    ) : AnimalPurpose(k, ratio, localized) {
        override val type: AnimalType = AnimalType.PIG

        object MEAT : PigPurpose(
            k = 1.0,
            ratio = FeedRatio(3.0, 0.6, 0.25, 0.05, 0.1),
            localized = "Мясо"
        )

        object BREEDING : PigPurpose(
            k = 1.25,
            ratio = FeedRatio(3.2, 0.5, 0.3, 0.1, 0.1),
            localized = "Разведение"
        )
    }

    sealed class CowPurpose(
        k: Double,
        ratio: FeedRatio,
        localized: String
    ) : AnimalPurpose(k, ratio, localized) {
        override val type: AnimalType = AnimalType.COW

        object MILK : CowPurpose(
            k = 1.15,
            ratio = FeedRatio(40.0, 0.4, 0.2, 0.3, 0.1),
            localized = "Молоко"
        )

        object MEAT : CowPurpose(
            k = 1.0,
            ratio = FeedRatio(35.0, 0.35, 0.25, 0.3, 0.1),
            localized = "Мясо"
        )

        object BREEDING : CowPurpose(
            k = 1.25,
            ratio = FeedRatio(38.0, 0.4, 0.25, 0.25, 0.1),
            localized = "Разведение"
        )
    }
}