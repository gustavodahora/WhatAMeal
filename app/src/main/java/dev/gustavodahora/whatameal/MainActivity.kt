package dev.gustavodahora.whatameal

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var appLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupClickEvents()
    }

    private fun setupViews() {
        appLogo = findViewById(R.id.app_logo)
    }

    private fun setupClickEvents() {
        appLogo.setOnClickListener {
            Toast.makeText(applicationContext, getString(R.string.this_is_a_soup), Toast.LENGTH_LONG).show()
        }
    }
}