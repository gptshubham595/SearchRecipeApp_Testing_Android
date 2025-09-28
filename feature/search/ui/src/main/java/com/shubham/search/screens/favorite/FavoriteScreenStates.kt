package com.shubham.search.screens.favorite

import com.shubham.common.utils.UiText
import com.shubham.search.domain.model.Recipe

object FavoriteScreenStates {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: List<Recipe>? = null
    )

    sealed interface Navigation {
        data class GoToRecipeDetailsScreen(val id: String) :
            Navigation
    }

    sealed interface Event {
        data object AlphabeticalSort : Event
        data object LessIngredientsSort : Event
        data object ResetSort : Event
        data class ShowDetails(val id: String) :
            Event

        data class DeleteRecipe(val recipe: Recipe) :
            Event

        data class GoToDetails(val id: String) :
            Event
    }

}