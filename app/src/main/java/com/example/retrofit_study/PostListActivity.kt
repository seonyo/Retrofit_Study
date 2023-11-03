package com.example.retrofit_study

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_study.Config.Companion.BASE_URL
import com.example.retrofit_study.api.APIService
import com.example.retrofit_study.api.AllPostResponse
import com.example.retrofit_study.api.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostListActivity : AppCompatActivity() {
    lateinit var apiService : APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)// 안드에서 지원하는 내 pc(10.0.2.2)에 연결해준다. localhost 사용 안 됨
            .addConverterFactory(GsonConverterFactory.create())// 필수적으로 들어가는 코드. body-parser의 역할과 동일
            .build()

        apiService = retrofit.create(APIService::class.java)// 아까 정의한 APIService 인터페이스를 만듦
        showPosts()
    }

    fun showPosts() {
        val call = apiService.getPosts()
        call.enqueue(object : Callback<AllPostResponse> {
            @SuppressLint("WrongViewCast")
            override fun onResponse(call: Call<AllPostResponse>, response: Response<AllPostResponse>) {
                if ( response.isSuccessful) {
                    val data : AllPostResponse? = response.body()
                    data?.let {
                        Log.d("mytag", it.result.toString())
                        val layoutManager = LinearLayoutManager(this@PostListActivity)
                        val adapter = PostListAdapter(it.result)
                        val recyclerView = findViewById<RecyclerView>(R.id.post_list)
                        recyclerView.setHasFixedSize(false)
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = adapter
                    }

                } else {
                    // 없습니다
                }
            }

            override fun onFailure(call: Call<AllPostResponse>, t: Throwable) {

            }

        })
    }
}