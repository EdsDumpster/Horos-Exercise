package com.example.horos_exercise.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.horos_exercise.R
import com.example.horos_exercise.SessionManager
import com.example.horos_exercise.ZodiacList

class DetailActivity : AppCompatActivity() {

    lateinit var nameView: TextView
    lateinit var dateView: TextView
    lateinit var iconView: ImageView

    lateinit var horoscope: ZodiacList

    lateinit var session: SessionManager
    lateinit var favoriteMenu: MenuItem

    var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        session = SessionManager(this)

        nameView = findViewById(R.id.ivName)
        dateView = findViewById(R.id.ivDate)
        iconView = findViewById(R.id.ivIcon)

        val id = intent.getStringExtra("HOROSCOPE_ID")!!

        horoscope = ZodiacList.getById(id)
        isFavorite = session.isFavorite(id)




        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.dates)

        nameView.setText(horoscope.name)
        dateView.setText(horoscope.dates)
        iconView.setImageResource(horoscope.icon)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_detail_menu, menu)

        favoriteMenu = menu.findItem(R.id.action_favorite)
        setFavoriteMenu()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_favorite -> {
                isFavorite = !isFavorite
                if (isFavorite) {
                    session.setFavorite(horoscope.id)
                } else {
                    session.setFavorite("")
                }
                setFavoriteMenu()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    fun setFavoriteMenu() {
        if (isFavorite) {
            favoriteMenu.setIcon(R.drawable.favorite_true_icon)
        } else {
            favoriteMenu.setIcon(R.drawable.favorite_false_icon)
        }
    }
}