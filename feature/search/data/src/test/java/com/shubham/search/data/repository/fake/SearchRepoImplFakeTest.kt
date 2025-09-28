package com.shubham.search.data.repository.fake

/**
 * APIS getRecipe(string) and getRecipeDetails(id)
 * Unit tests for SearchRepoImpl covering API responses for:
 * - Success responses
 * - Success with empty data
 * - Null responses
 * - Failure responses (HTTP error)
 * - Runtime exceptions
 * using Fake for mocking dependencies.
 */

import com.shubham.search.data.local.RecipeDao
import com.shubham.search.data.mappers.toDomain
import com.shubham.search.data.model.RecipeDTO
import com.shubham.search.data.model.RecipeDetailsResponse
import com.shubham.search.data.model.RecipeResponse
import com.shubham.search.data.remote.SearchApiService
import com.shubham.search.data.repository.SearchRepoImpl
import com.shubham.search.domain.model.Recipe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class SearchRepoImplFakeTest {
    private val searchApiService: SearchApiService = FakeSearchApiService()
    private val recipeDao: RecipeDao = FakeRecipeDao()

    @Test
    fun `test insert`() = runTest {
        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val recipe: Recipe = getFakeRecipeResponse().meals!!.first().toDomain()
        repo.insertRecipe(recipe)
        val allRecipes: List<Recipe> = repo.getAllRecipes().first()
        assertEquals(recipe, allRecipes.find { recipe.idMeal == it.idMeal })
    }

    @Test
    fun `test delete`() = runTest {
        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val recipe: Recipe = getFakeRecipeResponse().meals!!.first().toDomain()
        repo.insertRecipe(recipe)
        val allRecipes: List<Recipe> = repo.getAllRecipes().first()
        repo.deleteRecipe(recipe)

        assertEquals(null, allRecipes.find { recipe.idMeal == it.idMeal })
    }

    private fun getFakeRecipeResponse(): RecipeResponse {
        return RecipeResponse(
            meals = listOf(
                RecipeDTO(
                    dateModified = null,
                    idMeal = "idMeal",
                    strArea = "India",
                    strCategory = "category",
                    strYoutube = "strYoutube",
                    strTags = "tag1,tag2",
                    strMeal = "Chicken",
                    strSource = "strSource",
                    strMealThumb = "strMealThumb",
                    strInstructions = "strInstructions",
                    strCreativeCommonsConfirmed = null,
                    strIngredient1 = null,
                    strIngredient2 = null,
                    strIngredient3 = null,
                    strIngredient4 = null,
                    strIngredient5 = null,
                    strIngredient6 = null,
                    strIngredient7 = null,
                    strIngredient8 = null,
                    strIngredient9 = null,
                    strIngredient10 = null,
                    strIngredient11 = null,
                    strIngredient12 = null,
                    strIngredient13 = null,
                    strIngredient14 = null,
                    strIngredient15 = null,
                    strIngredient16 = null,
                    strIngredient17 = null,
                    strIngredient18 = null,
                    strIngredient19 = null,
                    strIngredient20 = null,
                    strMeasure1 = null,
                    strMeasure2 = null,
                    strMeasure3 = null,
                    strMeasure4 = null,
                    strMeasure5 = null,
                    strMeasure6 = null,
                    strMeasure7 = null,
                    strMeasure8 = null,
                    strMeasure9 = null,
                    strMeasure10 = null,
                    strMeasure11 = null,
                    strMeasure12 = null,
                    strMeasure13 = null,
                    strMeasure14 = null,
                    strMeasure15 = null,
                    strMeasure16 = null,
                    strMeasure17 = null,
                    strMeasure18 = null,
                    strMeasure19 = null,
                    strMeasure20 = null,
                    strDrinkAlternate = null,
                    strImageSource = "empty"
                )
            )
        )
    }

    private fun getFakeRecipeDetails(): RecipeDetailsResponse? {
        return getFakeRecipeResponse().meals?.first()?.let {
            RecipeDetailsResponse(
                meals = listOf(
                    it
                )
            )
        }
    }

}