package dev.gustavodahora.whatameal

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.squareup.picasso.Picasso
import dev.gustavodahora.whatameal.model.Ingredients
import dev.gustavodahora.whatameal.model.Meal
import dev.gustavodahora.whatameal.recipeapi.ApiUtil

class MainActivity : AppCompatActivity() {

    private lateinit var appLogo: ImageView
    private lateinit var btnSearchAMeal: Button
    private lateinit var recipeImage: ImageView
    private lateinit var recipeTitle: TextView
    private lateinit var recipeIconStart: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var containerItems: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupClickEvents()
    }

    private fun setupViews() {
        appLogo = findViewById(R.id.app_logo)
        btnSearchAMeal = findViewById(R.id.btn_search_a_meal)
        recipeImage = findViewById(R.id.recipe_image)
        recipeTitle = findViewById(R.id.recipe_title)
        recipeIconStart = findViewById(R.id.recipe_icon_start)
        recyclerView = findViewById(R.id.recycler_view)
        containerItems = findViewById(R.id.container_items)
    }

    private fun setupClickEvents() {
        appLogo.setOnClickListener {
            Toast.makeText(
                applicationContext,
                getString(R.string.this_is_a_soup),
                Toast.LENGTH_LONG
            ).show()
        }
        btnSearchAMeal.setOnClickListener {
            callApiRandom()
            removeStartButton()
        }
    }

    private fun callApiRandom() {
        val apiUtil = ApiUtil( this, this, this)
        apiUtil.callApi()
    }

    fun startRecycleView(meal: Meal) {
        recipeTitle.text = meal.strMeal
        meal.strMealThumb?.let {
            Picasso.get().load(it).error(R.drawable.error_image).into(recipeImage)
            setupMealThumb()
        }

        val ingredientsList = ArrayList<Ingredients>()
        if (meal.strIngredient1 != null && meal.strMeasure1 != null) {
            val ingredient = Ingredients(meal.strIngredient1, meal.strMeasure1)
            ingredientsList.add(ingredient)
        }


        val handler = Handler()
        handler.postDelayed({
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = CustomAdapter(ingredientsList)
            YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart {
                    containerItems.visibility = View.VISIBLE
                }
                .playOn(containerItems)
        }, 1000)
    }

    private fun removeStartButton() {
        recipeIconStart.visibility = View.GONE

        if (containerItems.visibility == View.VISIBLE) {
            YoYo.with(Techniques.FadeOut)
                .duration(500)
                .onStart {
                    containerItems.visibility = View.VISIBLE
                }
                .playOn(containerItems)
        }
    }

    private fun setupMealThumb() {
        val handler = Handler()
        handler.postDelayed({
            YoYo.with(Techniques.FadeIn)
                .duration(500)
                .onStart {
                    recipeIconStart.visibility = View.VISIBLE
                }
                .playOn(recipeIconStart)
        }, 1000)
    }

    class CustomAdapter(private val ingredientsList: List<Ingredients>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val measure: TextView
            val ingredient: TextView

            init {
                // Define click listener for the ViewHolder's View.
                measure = view.findViewById(R.id.measure)
                ingredient = view.findViewById(R.id.ingredient)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_items_recipe, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.measure.text = ingredientsList[position].measure
            viewHolder.ingredient.text = ingredientsList[position].ingredient
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = ingredientsList.size

    }

}