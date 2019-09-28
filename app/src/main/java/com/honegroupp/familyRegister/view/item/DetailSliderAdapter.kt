package com.honegroupp.familyRegister.view.item

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.honegroupp.familyRegister.R
import com.honegroupp.familyRegister.model.Item
import com.squareup.picasso.Picasso

class DetailSliderAdapter(val items: ArrayList<Item>, val context: Context) : PagerAdapter() {
    var listener: OnItemClickerListener? = null

    interface OnItemClickerListener {
        fun onItemClick(position: Int, items:ArrayList<Item>)
        fun onDownloadClick(position: Int, items:ArrayList<Item>)
        fun onShareClick(position: Int, items:ArrayList<Item>, imageView: ImageView)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.slide_detail_layout, container, false)

        val slideImageView = view.findViewById<ImageView>(R.id.detail_image)
        val slideHeaing = view.findViewById<TextView>(R.id.detail_heading)
        val slideDescription = view.findViewById<TextView>(R.id.detail_desc)

        val currItemUploads = items[position]


        // Load image to ImageView via its URL from Firebase Storage
        Picasso.get()
            .load(currItemUploads.imageURLs[0])
            .placeholder(R.mipmap.loading_jewellery)
            .into(slideImageView)
        slideHeaing.setText(currItemUploads.itemName)
        slideDescription.setText(currItemUploads.itemDescription)

        view.findViewById<Button>(R.id.detail_edit).setOnClickListener{
            val intent = Intent(context, ItemEdit::class.java)
            context.startActivity(intent)
        }

        // set on click listeners
        view.findViewById<ImageView>(R.id.detail_image).setOnClickListener{
            listener!!.onItemClick(position, items)
        }

        view.findViewById<Button>(R.id.detail_download).setOnClickListener{
            listener!!.onDownloadClick(position, items)
        }

        view.findViewById<Button>(R.id.detail_share).setOnClickListener{
            Picasso.get()
                .load(currItemUploads.imageURLs[0])
                .placeholder(R.mipmap.loading_jewellery)
                .into(slideImageView)
            listener!!.onShareClick(position, items, slideImageView)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}