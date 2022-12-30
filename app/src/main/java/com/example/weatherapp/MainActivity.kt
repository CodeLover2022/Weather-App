package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
          download_task()
        }
    }

    private fun download_task() {
        val API="39dcb0a847af8cb15ad1f42df8f7f953"
        val cityname = et_city.text.toString()
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$cityname&appid=$API"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    Log.d("tag", response.toString())
                    extract_details(response,cityname)
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })
        queue.add(request)
    }

    private fun extract_details(response: String?,city:String) {
        val obj1=JSONObject(response)
        val obj2=obj1.getJSONArray("weather")
        val obj3=obj2.getJSONObject(0)
        val obj4=obj3.getString("main")
        val obj5=obj1.getJSONObject("main")
        val obj6=obj5.getString("temp")
        val obj9=obj5.getString("pressure")
        val objs2=obj5.getString("sea_level")
        val obj8=obj5.getString("humidity")
        val obj7=obj6.toFloat()-273.00
        val obj_s=obj1.getJSONObject("wind")
        val r=obj_s.getString("speed")
        textView7.text=objs2
         tv_visible.text=r
        tv_pressure.text=obj9
        tv_temperature.text=obj7.toInt().toString()
        tv_city_name.text=city
        tv_weather.text=obj4
        tv_humidity1.text="$obj8 %"
        val ch=tv_weather.text.toString()
       when(ch)
       {
           "Haze" -> img_weather.setImageResource(R.drawable.hz_1)
           "Clear" -> img_weather.setImageResource(R.drawable.clear_sky3)
           "Clouds" -> img_weather.setImageResource(R.drawable.clouds_12)
           "fog" -> img_weather.setImageResource(R.drawable.fog_12)
           "thunderstorm" -> img_weather.setImageResource(R.drawable.thunderstorm)
           "Rain" -> img_weather.setImageResource(R.drawable.rains)
           "Smoke" -> img_weather.setImageResource(R.drawable.fog_12)
       }
//        tv_humidity.setText("Temp:-$t 'C")
    }
}