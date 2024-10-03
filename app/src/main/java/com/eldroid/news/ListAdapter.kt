package com.eldroid.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyListAdapter(private val items: List<NewsItems>, private val onItemClickListener: (NewsItems) -> Unit) :
    BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        val item = items[position]
        view.findViewById<TextView>(R.id.itemTitle).text = item.title
        view.setOnClickListener { onItemClickListener(item) }
        return view
    }
}