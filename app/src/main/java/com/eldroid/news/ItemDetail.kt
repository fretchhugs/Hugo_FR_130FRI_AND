package com.eldroid.news

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class ItemDetailFragment : Fragment() {

    private lateinit var item: NewsItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable("item")!!
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_detail, container, false)

        view.findViewById<TextView>(R.id.itemTitle).text = item.title
        view.findViewById<TextView>(R.id.itemDescription).text = item.description
        view.findViewById<ImageView>(R.id.itemImage).setImageResource(item.imageResId)

        return view
    }

    companion object {
        fun newInstance(item: NewsItems) =
            ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("item", item)
                }
            }
    }
}
