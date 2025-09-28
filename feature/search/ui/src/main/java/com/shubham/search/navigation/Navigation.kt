package com.shubham.search.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.shubham.common.navigation.FeatureApi
import com.shubham.common.navigation.NavigationRoute
import com.shubham.common.navigation.NavigationSubGraphRoute
import com.shubham.search.screens.details.RecipeDetailsStates
import com.shubham.search.screens.details.RecipeDetailsScreen
import com.shubham.search.screens.details.RecipeDetailsViewModel
import com.shubham.search.screens.favorite.FavoriteScreen
import com.shubham.search.screens.favorite.FavoriteScreenStates
import com.shubham.search.screens.favorite.FavoriteViewModel
import com.shubham.search.screens.recipe_list.RecipeList
import com.shubham.search.screens.recipe_list.RecipeListScreen
import com.shubham.search.screens.recipe_list.RecipeListViewModel

interface SearchFeatureApi : FeatureApi


class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.Search.route,
            startDestination = NavigationRoute.RecipeList.route
        ) {

            composable(route = NavigationRoute.RecipeList.route) {
                val viewModel = hiltViewModel<RecipeListViewModel>()
                RecipeListScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                ) { mealId ->
                    viewModel.onEvent(RecipeList.Event.GoToRecipeDetails(mealId))
                }

            }

            composable(route = NavigationRoute.RecipeDetails.route) {
                val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val mealId = it.arguments?.getString("id")
                LaunchedEffect(key1 = mealId) {
                    mealId?.let {
                        viewModel.onEvent(RecipeDetailsStates.Event.FetchRecipeDetails(it))
                    }
                }
                RecipeDetailsScreen(
                    viewModel = viewModel,
                    onNavigationClick = {
                        viewModel.onEvent(RecipeDetailsStates.Event.GoToRecipeListScreen)
                    },
                    onFavoriteClick = {
                        viewModel.onEvent(RecipeDetailsStates.Event.InsertRecipe(it))
                    },
                    onDelete = {
                        viewModel.onEvent(RecipeDetailsStates.Event.DeleteRecipe(it))
                    }, navHostController = navHostController
                )
            }

            composable(NavigationRoute.FavoriteScreen.route) {
                val viewModel = hiltViewModel<FavoriteViewModel>()
                FavoriteScreen(
                    navHostController = navHostController,
                    viewModel = viewModel,
                    onClick = { mealId ->
                        viewModel.onEvent(FavoriteScreenStates.Event.GoToDetails(mealId))
                    })
            }

        }


    }
}

