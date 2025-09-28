package com.shubham.search.data.repository.mockito

import com.shubham.search.data.local.RecipeDao
import com.shubham.search.data.mappers.toDomain
import com.shubham.search.data.mappers.toRecipeDetails
import com.shubham.search.data.model.RecipeDTO
import com.shubham.search.data.model.RecipeDetailsResponse
import com.shubham.search.data.model.RecipeResponse
import com.shubham.search.data.remote.SearchApiService
import com.shubham.search.data.repository.SearchRepoImpl
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import kotlin.test.assertEquals

/**
 * APIS getRecipe(string) and getRecipeDetails(id)
 * Unit tests for SearchRepoImpl covering API responses for:
 * - Success responses
 * - Success with empty data
 * - Null responses
 * - Failure responses (HTTP error)
 * - Runtime exceptions
 * using Mockito for mocking dependencies.
 */

class SearchRepoImplTest {
    private val searchApiService: SearchApiService = Mockito.mock()
    private val recipeDao: RecipeDao = Mockito.mock()

    @Test
    fun `test success`() = runTest {
        Mockito.`when`(searchApiService.getRecipes("chicken"))
            .thenReturn(Response.success(200, getFakeRecipeResponse()))

        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipes("chicken")
        assertEquals(getFakeRecipeResponse().meals?.map { it.toDomain() }, response.getOrThrow())

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

    @Test
    fun `test null response from backend`() = runTest {
        Mockito.`when`(searchApiService.getRecipes("chicken"))
            .thenReturn(Response.success(200, RecipeResponse()))

        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipes("chicken")

        val errorMessage = "error occurred"

        assertEquals(errorMessage, response.exceptionOrNull()?.message)
    }

    @Test
    fun `test failure response from backend`() = runTest {
        Mockito.`when`(searchApiService.getRecipes("chicken"))
            .thenReturn(Response.error(404, ResponseBody.create(null, "")))

        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipes("chicken")

        val errorMessage = "error occurred"

        assertEquals(errorMessage, response.exceptionOrNull()?.message)
    }

    @Test
    fun `test exception thrown from backend`() = runTest {
        Mockito.`when`(searchApiService.getRecipes("chicken"))
            .thenThrow(RuntimeException("Something went wrong"))

        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipes("chicken")

        val errorMessage = "Something went wrong"

        assertEquals(errorMessage, response.exceptionOrNull()?.message)
    }

    @Test
    fun `test success recipe details`() = runTest {
        Mockito.`when`(searchApiService.getRecipeDetails("52771"))
            .thenReturn(Response.success(200, getFakeRecipeDetails()))

        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipeDetails("52771")
        assertEquals(
            getFakeRecipeDetails()?.meals?.first()?.toRecipeDetails(),
            response.getOrThrow()
        )

    }

    @Test
    fun `test success recipe with empty details`() = runTest {
        Mockito.`when`(searchApiService.getRecipeDetails("52771"))
            .thenReturn(Response.success(200, RecipeDetailsResponse(meals = listOf())))

        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipeDetails("52771")
        val errorMessage = "error occurred"
        assertEquals(
            errorMessage,
            response.exceptionOrNull()?.message
        )
    }

    @Test
    fun `test null response from backend for recipe details`() = runTest {
        Mockito.`when`(searchApiService.getRecipeDetails("52771"))
            .thenReturn(Response.success(200, RecipeDetailsResponse()))

        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipeDetails("52771")

        val errorMessage = "error occurred"

        assertEquals(errorMessage, response.exceptionOrNull()?.message)
    }

    @Test
    fun `test failure response from backend for recipe details`() = runTest {
        Mockito.`when`(searchApiService.getRecipeDetails("52771"))
            .thenReturn(Response.error(404, ResponseBody.create(null, "")))
        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipeDetails("52771")
        val errorMessage = "error occurred"
        assertEquals(errorMessage, response.exceptionOrNull()?.message)
    }

    @Test
    fun `test exception thrown from backend for recipe details`() = runTest {
        Mockito.`when`(searchApiService.getRecipeDetails("52771"))
            .thenThrow(RuntimeException("Something went wrong"))

        val repo = SearchRepoImpl(searchApiService, recipeDao)
        val response = repo.getRecipeDetails("52771")

        val errorMessage = "Something went wrong"

        assertEquals(errorMessage, response.exceptionOrNull()?.message)
    }
}