package dev.gustavodahora.whatameal.recipeapi

import dev.gustavodahora.whatameal.model.Recipe
import retrofit2.Call
import retrofit2.http.GET

interface RecipeCall {
    @GET("random.php")
    fun call(): Call<Recipe>
}
