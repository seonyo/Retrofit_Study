package com.example.retrofit_study

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_study.api.APIService
import com.example.retrofit_study.api.PostResponse
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostReadActivity : AppCompatActivity() {
    lateinit var apiService : APIService
    var id : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_read)
        
        // id 받아서 content 출력하기
        id = intent.getIntExtra("id", -1)!!
        Log.d("mytag", id.toString())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")// 안드에서 지원하는 내 pc(10.0.2.2)에 연결해준다. localhost 사용 안 됨
            .addConverterFactory(GsonConverterFactory.create())// 필수적으로 들어가는 코드. body-parser의 역할과 동일
            .build()

        apiService = retrofit.create(APIService::class.java)// 아까 정의한 APIService 인터페이스를 만듦
        getPost(id)
    }

    fun getPost( id: Int) {
        val call = apiService.getPost(id)
        call.enqueue(object : Callback<PostResponse> {
            @SuppressLint("WrongViewCast")
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if ( response.isSuccessful) {
                    val data : PostResponse? = response.body()
                    data?.let {
                        findViewById<TextView>(R.id.post_title).text = "제목: "+it.result.title
                        findViewById<TextView>(R.id.post_author).text = "글쓴이: "+it.result.author
                        findViewById<TextView>(R.id.post_content).text = "내용: "+it.result.content
                    }
                } else {
                    // 없습니다
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.w("mytag", "failed to call API : "+t.message)
            }

        })
    }
}