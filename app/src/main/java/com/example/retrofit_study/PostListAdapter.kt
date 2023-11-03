package com.example.retrofit_study

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_study.api.Post

class PostListAdapter(val dataList: List<Post>) : RecyclerView.Adapter<PostListAdapter.ItemViewHolder>(){
    // 내부크래스 ItemViewHolder 만들기
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view)// ViewHolder : 뷰를 가지고 있는 것

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)// layout.xml -> View
        return ItemViewHolder(view)// ItemViewHolder에 view를 주며 return
    }
    // 얘가 제일 중요 : view 보여줄때
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]// 보여줄 항목
        val view = holder.view
        view.findViewById<TextView>(R.id.post_title).text = item.title// title 변경
    }
    // 수동
    override fun getItemViewType(position: Int) = R.layout.post_list_item// layout 리소스의 아이디 반환
//    override fun getItemViewType(position: Int) : Int {
//
//        return R.layout.post_list_item
//    }
    override fun getItemCount() = dataList.size
}