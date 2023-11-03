package com.example.retrofit_study.api


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

// 추상메서드를 정의해야 함
// 우리가 만든 api에 엔드포인트가 5개가 있기 때문에 5개를 다 정의해야 함

// 왜 id만 val인가. 왜 createdAt가 String이 아니라 Date인가
// val은 상수
// String으로 했는데 왜 Date?
// 내부적으로 컨버터가 작동해서 String으로 변환해준다? (찾아보는 거 필요)
data class Post(val id: Int, val title: String, var author: String, val createdAt: Date, var content: String)
data class AllPostResponse(val result: List<Post>)
data class PostResponse(val result: Post)
data class PostCreateRequest(val title: String, val author: String, val content: String)
data class StringResponse(val result: String)// result: ok
data class DeletePostResponse(val id: Int)

interface APIService {
    @GET("/posts")
    fun getPosts() : Call<AllPostResponse>// Call은 httpOk, <>: body안에 있는 데이터의 타입을 넣어야 함

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: Int) : Call<PostResponse>// 글 하나 불러오기

    @POST("/posts")
    // PostCreateRequest에 title, content, author의 정보가 있음
    fun createPost(@Body request: PostCreateRequest) : Call<StringResponse>// 데이터가 json으로 바뀌어서 body로 들어간다

    // DELETE 요청 보내는 deletePost 메서드 정의
    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: Int) : Call<StringResponse>
}