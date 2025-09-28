package com.shubham.search.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.shubham.common.utils.NetworkResult
import com.shubham.common.utils.UiText
import com.shubham.search.domain.model.Recipe
import com.shubham.search.domain.model.RecipeDetails
import com.shubham.search.domain.use_cases.DeleteRecipeUseCase
import com.shubham.search.domain.use_cases.GetRecipeDetailsUseCase
import com.shubham.search.domain.use_cases.InsertRecipeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase
) :
    ViewModel() {

    private val _uiState =
        MutableStateFlow(com.shubham.search.screens.details.RecipeDetailsStates.UiState())
    val uiState: StateFlow<com.shubham.search.screens.details.RecipeDetailsStates.UiState> get() = _uiState.asStateFlow()

    private val _navigation =
        Channel<com.shubham.search.screens.details.RecipeDetailsStates.Navigation>()
    val navigation: Flow<com.shubham.search.screens.details.RecipeDetailsStates.Navigation> get() = _navigation.receiveAsFlow()

    fun onEvent(event: com.shubham.search.screens.details.RecipeDetailsStates.Event) {
        when (event) {
            is com.shubham.search.screens.details.RecipeDetailsStates.Event.FetchRecipeDetails -> recipeDetails(
                event.id
            )

            com.shubham.search.screens.details.RecipeDetailsStates.Event.GoToRecipeListScreen -> viewModelScope.launch {
                _navigation.send(com.shubham.search.screens.details.RecipeDetailsStates.Navigation.GoToRecipeListScreen)
            }

            is com.shubham.search.screens.details.RecipeDetailsStates.Event.DeleteRecipe -> {
                deleteRecipeUseCase.invoke(event.recipeDetails.toRecipe())
                    .launchIn(viewModelScope)

            }

            is com.shubham.search.screens.details.RecipeDetailsStates.Event.InsertRecipe -> {
                insertRecipeUseCase.invoke(event.recipeDetails.toRecipe())
                    .launchIn(viewModelScope)
            }

            is com.shubham.search.screens.details.RecipeDetailsStates.Event.GoToMediaPlayer -> {
                viewModelScope.launch {
                    _navigation.send(
                        com.shubham.search.screens.details.RecipeDetailsStates.Navigation.GoToMediaPlayer(
                            event.youtubeUrl
                        )
                    )
                }
            }
        }
    }

    private fun recipeDetails(id: String) = getRecipeDetailsUseCase.invoke(id)
        .onEach { result ->
            when (result) {
                is NetworkResult.Error -> {
                    _uiState.update {
                        com.shubham.search.screens.details.RecipeDetailsStates.UiState(
                            error = UiText.RemoteString(result.message.toString())
                        )
                    }
                }

                is NetworkResult.Loading -> _uiState.update {
                    com.shubham.search.screens.details.RecipeDetailsStates.UiState(
                        isLoading = true
                    )
                }

                is NetworkResult.Success -> _uiState.update {
                    com.shubham.search.screens.details.RecipeDetailsStates.UiState(
                        data = result.data
                    )
                }

            }

        }.launchIn(viewModelScope)

    fun RecipeDetails.toRecipe(): Recipe {
        return Recipe(
            idMeal,
            strArea,
            strMeal,
            strMealThumb,
            strCategory,
            strTags,
            strYoutube,
            strInstructions
        )
    }

}