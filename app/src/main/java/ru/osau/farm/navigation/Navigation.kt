package ru.osau.farm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.osau.farm.presentation.input.InputIntentScreen
import ru.osau.farm.presentation.input.InputTargetScreen
import ru.osau.farm.presentation.input.InputViewModel
import ru.osau.farm.presentation.price.EditFeedPriceScreen
import ru.osau.farm.presentation.result.ResultScreen

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: InputViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.InputIntent.route
    ) {
        composable(Screen.InputIntent.route) {
            InputIntentScreen(
                viewModel = viewModel,
                onOpenSettingsClicked = {
                    navController.navigate(Screen.EditFeedPrice.route)
                },
                onNavigateToTarget = {
                    navController.navigate(Screen.InputTarget.route)
                }
            )
        }

        composable(Screen.InputTarget.route) {
            InputTargetScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToResult = {
                    navController.navigate(Screen.Result.route)
                }
            )
        }

        composable(Screen.Result.route) {
            ResultScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    viewModel.resetState()
                    navController.navigate(Screen.InputIntent.route) {
                        popUpTo(Screen.InputIntent.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.EditFeedPrice.route) {
            EditFeedPriceScreen(
                viewModel = viewModel,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 