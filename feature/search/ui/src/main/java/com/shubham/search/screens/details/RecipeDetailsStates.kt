package com.shubham.search.screens.details

import com.shubham.common.utils.UiText
import com.shubham.search.domain.model.RecipeDetails

object RecipeDetailsStates {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: RecipeDetails? = null
    )

    sealed interface Navigation {
        data object GoToRecipeListScreen :
            Navigation
        data class GoToMediaPlayer(val youtubeUrl: String) :
            Navigation
    }

    sealed interface Event {

        data class FetchRecipeDetails(val id: String) :
            Event

        data class InsertRecipe(val recipeDetails: RecipeDetails) :
            Event
        data class DeleteRecipe(val recipeDetails: RecipeDetails) :
            Event

        data object GoToRecipeListScreen : Event

        data class GoToMediaPlayer(val youtubeUrl: String) :
            Event

    }

}
