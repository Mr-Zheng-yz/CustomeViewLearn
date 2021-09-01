package com.example.customviewprojdct.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.customviewprojdct.R

class ClassButtonAdapter(var onItemClickListener: ((Triple<String,String,Class<out View>?>) -> Unit)? = null) :
    RecyclerView.Adapter<ClassButtonAdapter.ButtonViewHolder>() {

    //first:类型 second：按钮名
    val data: MutableList<Triple<String, String,Class<out View>?>> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_button_item, parent, false)
        return ButtonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.button.text = data[position].second
        holder.button.setOnClickListener {
            onItemClickListener?.invoke(data[position])
        }
    }

    override fun getItemCount() = data.size

    inner class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var button: Button = view.findViewById(R.id.button)
    }
}