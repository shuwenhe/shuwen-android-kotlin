package com.example.shuwen

import android.os.Bundle
// new add import
import android.widget.TextView

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shuwen.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.concurrent.Executors
import java.net.URL

import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var textView: TextView
    //super.onCreate(savedInstanceState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

private fun fetchUserData(){
    val executorService = Executors.newSingleThreadExecutor()
    executorService.execute{
        var connection: HttpURLConnection? = null
        try {
            val url = URL("http://www.xstiku.com/getUserData")
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK){
                val inputStreamReader = InputStreamReader(connection.inputStream)
                val reader = BufferedReader(inputStreamReader)
                val response = StringBuilder()
                var inputLine: String?
                while (reader.readLine().also { inputLine = it } != null){
                    response.append(inputLine)
                }
                reader.close()
                
            }
        }catch (e:Exception){
//            runOnUiThread{
//                Log.e("MainActivity","网络请求失败：${e.message}")
//            }
        }
    }
}