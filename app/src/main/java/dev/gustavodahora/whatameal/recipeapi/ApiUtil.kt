package dev.gustavodahora.whatameal.recipeapi

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import dev.gustavodahora.whatameal.MainActivity
import dev.gustavodahora.whatameal.model.Meal
import dev.gustavodahora.whatameal.model.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtil(
    var context: Context?,
    var activity: Activity?,
    var mainActivity: MainActivity?
) {

    lateinit var meal: Meal

    companion object {
        const val baseUrl = "https://www.themealdb.com/api/json/v1/1/"
    }

    fun callApi() {
        try {
            // Retrofit Builder
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // instance for interface
            val recipeCall: RecipeCall = retrofit.create(RecipeCall::class.java)
            val call: Call<Recipe> = recipeCall.call()
            call.enqueue(object : Callback<Recipe?> {
                override fun onResponse(
                    call: Call<Recipe?>,
                    response: Response<Recipe?>
                ) {
                    meal = response.body()!!.meals[0]
                }

                override fun onFailure(call: Call<Recipe?>, t: Throwable) {
                    Toast.makeText(context, "PALMEIRAS MUNDIAL", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Log.e("FIOTE", e.toString())
        }
    }
}