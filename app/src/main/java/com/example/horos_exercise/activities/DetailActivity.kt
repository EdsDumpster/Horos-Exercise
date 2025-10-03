package com.example.horos_exercise.activities

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailActivity : AppCompatActivity() {

    lateinit var nameView: TextView
    lateinit var dateView: TextView
    lateinit var iconView: ImageView
    lateinit var horoscope: ZodiacList
    lateinit var session: SessionManager
    lateinit var favoriteMenu: MenuItem

    lateinit var progressionIndicator: LinearProgressIndicator

    lateinit var luckView: TextView

    lateinit var navigationBottom: BottomNavigationView

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

        progressionIndicator = findViewById(R.id.progressIndicator)
        luckView = findViewById(R.id.luckView)
        navigationBottom = findViewById(R.id.ivNavigation)
        navigationBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_daily -> {
                    getLuck("daily")
                    true
                }
                R.id.action_weekly -> {
                    getLuck("weekly")
                    true
                }
                R.id.action_monthly -> {
                    getLuck("monthly")
                    true
                }
                else -> false
            }
        }

        getLuck()
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

    fun getLuck(period: String = "daily") {
        progressionIndicator.show()
        luckView.text = "Consulting the stars..."
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/$period?sign=${horoscope.id}&day=TODAY")
            // HTTP Connexion
            val connection = url.openConnection() as HttpsURLConnection
            // Method
            connection.setRequestMethod("GET")

            try {
                // Response code
                val responseCode = connection.getResponseCode()

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val response = readStream(connection.inputStream)

                    val jsonResponse = JSONObject(response)
                    val result = jsonResponse.getJSONObject("data").getString("horoscope_data")

                    CoroutineScope(Dispatchers.Main).launch {
                        luckView.text = result
                        progressionIndicator.hide()
                    }

                    Log.i("API", result)
                } else {
                    Log.e("API", "Server response: $responseCode")
                }
            } catch (e: Exception) {
                Log.e("API", "Error", e)
            } finally {
                connection.disconnect()
            }
        }
    }

    fun readStream (input: InputStream) : String {
        val reader = BufferedReader(InputStreamReader(input))
        val response = StringBuffer()
        var inputLine: String? = null

        while ((reader.readLine().also { inputLine = it }) != null) {
            response.append(inputLine)
        }
        reader.close()
        return response.toString()
    }
}