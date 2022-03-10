package dev.gustavodahora.whatameal

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.gustavodahora.whatameal.recipeapi.ApiUtil

class MainActivity : AppCompatActivity() {

    private lateinit var appLogo: ImageView
    private lateinit var btnSearchAMeal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupClickEvents()
    }

    private fun setupViews() {
        appLogo = findViewById(R.id.app_logo)
        btnSearchAMeal = findViewById(R.id.btn_search_a_meal)
    }

    private fun setupClickEvents() {
        appLogo.setOnClickListener {
            Toast.makeText(
                applicationContext,
                getString(R.string.this_is_a_soup),
                Toast.LENGTH_LONG
            ).show()
        }
        btnSearchAMeal.setOnClickListener { callApiRandom() }
    }

    private fun callApiRandom() {
        val apiUtil = ApiUtil( this, this, this)
        apiUtil.callApi()
    }
}