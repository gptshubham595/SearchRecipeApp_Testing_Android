package com.shubham.search.data.repository.fake

import com.shubham.search.data.model.RecipeDTO
import com.shubham.search.data.model.RecipeDetailsResponse
import com.shubham.search.data.model.RecipeResponse
import com.shubham.search.data.remote.SearchApiService
import com.shubham.search.domain.model.Recipe
import retrofit2.Response

class FakeSearchApiService : SearchApiService {
    val list = mutableListOf<Recipe>()

    override suspend fun getRecipes(s: String): Response<RecipeResponse> {
        return Response.success(200, getFakeRecipeResponse())
    }

    override suspend fun getRecipeDetails(i: String): Response<RecipeDetailsResponse> {
        return Response.success(200, getFakeRecipeDetails())
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