package com.example.retrofit_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofit_study.api.APIService
import com.example.retrofit_study.api.AllPostResponse
import com.example.retrofit_study.api.DeletePostResponse
import com.example.retrofit_study.api.PostCreateRequest
import com.example.retrofit_study.api.PostResponse
import com.example.retrofit_study.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // 싱글톤 패턴 : 객체를 하나만 만들 수 있도록 하는 패턴 (전역변수의 고급버전)
    lateinit var apiService : APIService// APIService를 여러군데에 사용할 수 있으므로 속성으로 만든 것

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")// 안드에서 지원하는 내 pc(10.0.2.2)에 연결해준다. localhost 사용 안 됨
            .addConverterFactory(GsonConverterFactory.create())// 필수적으로 들어가는 코드. body-parser의 역할과 동일
            .build()

        apiService = retrofit.create(APIService::class.java)// 아까 정의한 APIService 인터페이스를 만듦
//        getPosts()
//        getPost(5)
//        deletePost(4)
//        createPost()
        modifyPost(2, mapOf(...))
    }

    fun createPost() {
        val call = apiService.createPost(PostCreateRequest("android title", "android author", "android content"))
        call.enqueue(object : Callback<StringResponse> {
            override fun onResponse(
                call: Call<StringResponse>,
                response: Response<StringResponse>
            ) {

            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {

            }
        })
    }

    fun getPosts() {
        val call = apiService.getPosts()// 요청 보내는 준비
        // 비동기처리 (콜백메서드) - 익명클래스(정의,구현을 동시에(상속도 받고 구현도 하고)) 객체를 만든다
        call.enqueue(object : Callback<AllPostResponse> {// queue : 요청을 보내는 큐. enqueue : 큐에 데이터를 넣는다. 데이터가 큐라는 곳에 하나하나씩 쌓이는 형식으로 데이터가 전달된다
            // 요청 성공
            override fun onResponse(call: Call<AllPostResponse>, response: Response<AllPostResponse> ) {// response: 찐 응답 객체(node에서 보낸 데이터)
                val data : AllPostResponse? = response.body()// body데이터
                data?.let {// 범위함수: ?. => null일수도 있다 , it: 함수형인터페이스. 함수의 인자가 하나일 때 it으로 접근 가능. 근데 다 필요없고 it이 데이터임(AllPostResponse?에서 ?빠진 버전)(nullable타입 공부 필요함.
                    Log.d("mytag", it.result.toString())
                }
            }

            // 요청 실패 (네트워크에 의해 요청 자체를 보낼 수 없는. 일단 요청이 가면 무조건 reponse에서 처리)
            override fun onFailure(call: Call<AllPostResponse>, t: Throwable) {

            }
        })
    }

    fun getPost( id: Int) {
        val call = apiService.getPost(id)
        call.enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if ( response.isSuccessful) {
                    val data : PostResponse? = response.body()
                    data?.let {
                        Log.d("mytag", it.result.toString())
                    }
                } else {
                    // 없습니다
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {

            }

        })
    }

    fun deletePost(id: Int) {
        val call = apiService.deletePost(id)
        call.enqueue(object : Callback<StringResponse> {// 익명클래스므로 추상메서드 구현 필요
            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                // if문도 필요없음. 실행했을 때 잘 지워지는지만 확인하기
               if ( response.isSuccessful) {
                   val data : StringResponse? = response.body()
                   data?.let {
                       Log.d("mydelete", it.result.toString())
                   }
               } else {
                   
               }
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                
            }
        })
    }
}