package com.example.retrofit_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class PostReadActivity : AppCompatActivity() {
    var id : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_read)
        
        // id 받아서 content 출력하기
        id = intent.getIntExtra("id", -1)!!
        Log.d("mytag", id.toString())
    }
}