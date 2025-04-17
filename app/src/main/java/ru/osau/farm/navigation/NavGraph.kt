package ru.osau.farm.navigation

sealed class Screen(val route: String) {
    object InputIntent : Screen("input_intent")
    object InputTarget : Screen("input_target")
    object Result : Screen("result")
    object EditFeedPrice : Screen("edit_feed_price")
    // TODO: Add other screens
}

sealed class NavGraph(val route: String) {
    object Main : NavGraph("main")
} 