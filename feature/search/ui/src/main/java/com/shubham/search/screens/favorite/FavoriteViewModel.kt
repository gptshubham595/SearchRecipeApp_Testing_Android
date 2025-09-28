package com.shubham.search.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.search.domain.model.Recipe
import com.shubham.search.domain.use_cases.DeleteRecipeUseCase
import com.shubham.search.domain.use_cases.GetAllRecipesFromLocalDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllRecipesFromLocalDbUseCase: GetAllRecipesFromLocalDbUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) :
    ViewModel() {

    private var originalList = mutableListOf<Recipe>()

    private val _uiState = MutableStateFlow(FavoriteScreenStates.UiState())
    val uiState: StateFlow<FavoriteScreenStates.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<FavoriteScreenStates.Navigation>()
    val navigation: Flow<FavoriteScreenStates.Navigation> = _navigation.receiveAsFlow()

    init {
        getRecipeList()
    }

    fun onEvent(event: FavoriteScreenStates.Event) {
        when (event) {
            FavoriteScreenStates.Event.AlphabeticalSort -> alphabeticalSort()
            FavoriteScreenStates.Event.LessIngredientsSort -> lessIngredientsSort()
            FavoriteScreenStates.Event.ResetSort -> resetSort()
            is FavoriteScreenStates.Event.ShowDetails -> viewModelScope.launch {
                _navigation.send(FavoriteScreenStates.Navigation.GoToRecipeDetailsScreen(event.id))
            }

            is FavoriteScreenStates.Event.DeleteRecipe -> deleteRecipe(event.recipe)
            is FavoriteScreenStates.Event.GoToDetails -> viewModelScope.launch {
                _navigation.send(FavoriteScreenStates.Navigation.GoToRecipeDetailsScreen(event.id))
            }
        }
    }

    private fun deleteRecipe(recipe: Recipe) = deleteRecipeUseCase.invoke(recipe)
        .launchIn(viewModelScope)

    private fun getRecipeList() =
        viewModelScope.launch {
            getAllRecipesFromLocalDbUseCase.invoke().collectLatest { list ->
                originalList = list.toMutableList()
                _uiState.update { FavoriteScreenStates.UiState(data = list) }
            }
        }


    fun alphabeticalSort() =
        _uiState.update { FavoriteScreenStates.UiState(data = originalList.sortedBy { it.strMeal }) }

    fun lessIngredientsSort() =
        _uiState.update { FavoriteScreenStates.UiState(data = originalList.sortedBy { it.strInstructions.length }) }

    fun resetSort() {
        _uiState.update { FavoriteScreenStates.UiState(data = originalList) }
    }

}

