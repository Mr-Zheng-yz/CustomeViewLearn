package com.example.customviewprojdct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data: ArrayList<Any> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, null)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bindView(data[position] as String)
    }

    override fun getItemCount() = data.size

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTextView: TextView = view.findViewById(R.id.tv_text)

        fun bindView(str: String) {
            tvTextView.text = str
        }
    }

    fun addAll(d: ArrayList<Any>) {
        this.data.addAll(d)
    }

    fun add(d: Any) {
        this.data.add(d)
    }

    fun clear() {
        this.data.clear()
    }

}