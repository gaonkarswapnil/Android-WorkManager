package com.example.workmanager.workmanagernetworkapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanager.R

class UserAdapter(private val users: List<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.itemView.findViewById<TextView>(R.id.tvUserName).text = "${user.first_name} ${user.last_name}"
        holder.itemView.findViewById<TextView>(R.id.tvUserEmail).text = user.email
        holder.itemView.findViewById<TextView>(R.id.tvUserPhone).text = user.phone_no.toString()
        holder.itemView.findViewById<TextView>(R.id.tvUserFlag).text = user.flag.toString()
    }
}