package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com"

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)


        GlobalScope.launch(Dispatchers.IO){
            val comments = api.getComments().await()
            for(comment in comments) {
                Log.d(TAG, comment.toString())
            }
        }

//        api.getComments().enqueue(object : Callback<List<Comment>> {
//            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
//                Log.e(TAG, "ERROR: $t")
//            }
//
//            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
//                if(response.isSuccessful) {
//                    response.body()?.let {
//                        for(comment in it) {
//                            Log.d(TAG, comment.toString())
//                        }
//                    }
//                }
//            }
//        })
    }
}