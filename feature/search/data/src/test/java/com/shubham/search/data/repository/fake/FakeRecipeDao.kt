package com.shubham.search.data.repository.fake

import com.shubham.search.data.local.RecipeDao
import com.shubham.search.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRecipeDao: RecipeDao{
    val list = mutableListOf<Recipe>()

    override suspend fun insertRecipe(recipe: Recipe) {
        list.add(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        list.remove(recipe)
    }

    override fun getAllRecipes(): Flow<List<Recipe>> = flowOf(list)

    override suspend fun updateRecipe(recipe: Recipe) {
        // Safer approach: replace the list atomically
        val updatedList = list.map { if (it.idMeal == recipe.idMeal) recipe else it }
        list.clear()
        list.addAll(updatedList)


    }
}