package com.honegroupp.familyRegister.view.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.honegroupp.familyRegister.R
import com.honegroupp.familyRegister.model.Item
import com.squareup.picasso.Picasso

class ListViewAapter(val items: ArrayList<Item>, val mActivity:AppCompatActivity): BaseAdapter(){

    private val nameList : ArrayList<String> = ArrayList()
    private val imageURLList : ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val layout : View = View.inflate(mActivity, R.layout.item_listview, null)
        val picture : ImageView = layout.findViewById(R.id.picture)
        val name : TextView = layout.findViewById(R.id.name)

        for (item in items){
            imageURLList.add(item.imageURLs[0])
            nameList.add(item.itemName)
        }

        //set image to imageView
        Picasso.get()
                .load(imageURLList[position])
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(picture)

        name.text = nameList[position]

        return layout
    }

}