package com.example.coroutineapp.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coroutineapp.R
import com.example.coroutineapp.data.local.entity.User
import kotlinx.android.synthetic.main.item_layout.view.*

class UserAdapter(private val  user:ArrayList<User>):RecyclerView.Adapter<UserAdapter.DataViewHolder>() {


    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.textViewUserName.text = user.name
            itemView.textViewUserEmail.text = user.email
            Glide.with(itemView.imageViewAvatar.context)
                .load(user.avatar)
                .into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = user.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(user[position])

    fun addData(list: List<User>) {
        user.addAll(list)
    }

}