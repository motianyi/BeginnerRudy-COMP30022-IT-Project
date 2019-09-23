package com.honegroupp.familyRegister.view.itemList

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.honegroupp.familyRegister.R
import com.honegroupp.familyRegister.model.Item
import com.squareup.picasso.Picasso

class ItemListAdapter(val items: ArrayList<Item>, val mContext: Context) :
    RecyclerView.Adapter<ItemListAdapter.ImageViewHolder>() {

//    var listener: OnItemClickerListener? = null
    private val STORAGE_PERMISSION_CODE: Int = 1000

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currItem = items[position]

        holder.textViewName.text = currItem.itemName
        // Load image to ImageView via its URL from Firebase Storage
        Picasso.get()
            .load(currItem.imageURLs[0])
            .placeholder(R.mipmap.ic_launcher)
            .fit()
            .centerCrop()
            .into(holder.imageView)
        Log.d("url000", currItem.imageURLs[0])
    }

    override fun getItemCount(): Int {
        return items.size
    }


//    interface OnItemClickerListener {
//        fun onItemClick(position: Int)
//        fun onWhatEverClick(position: Int)
//        fun onDeleteClick(position: Int)
//        fun onDownloadClick(position: Int,item:ArrayList<ItemUpload>)
//    }

    inner class ImageViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem)
//        , View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener
    {
        val textViewName: TextView = viewItem.findViewById(R.id.txt_name)
        val imageView: ImageView = viewItem.findViewById(R.id.img_upload)

//        init {
//            viewItem.setOnClickListener(this)
//            viewItem.setOnCreateContextMenuListener(this)
//        }

//        override fun onMenuItemClick(p0: MenuItem?): Boolean {
//            if (listener != null) {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    when (p0?.itemId) {
//                        1 -> {
//                            listener!!.onWhatEverClick(position)
//                            return true
//                        }
//                        2 -> {
//                            listener!!.onDeleteClick(position)
//                            return true
//                        }
//                        3 -> {
//                            listener!!.onDownloadClick(position,items)
//                            return true
//                        }
//                    }
//                }
//            }
//            return false
//        }

//        override fun onCreateContextMenu(p0: ContextMenu?, p1: View?, p2: ContextMenu.ContextMenuInfo?) {
//            p0?.setHeaderTitle("Select Action")
//            val doWatEver = p0?.add(Menu.NONE, 1, 1, "Do Whatever: ")
//            val delete = p0?.add(Menu.NONE, 2, 2, "Do Delete: ")
//            val save = p0?.add(Menu.NONE, 3, 3, "Do Save: ")
//
//            doWatEver?.setOnMenuItemClickListener(this)
//
//            delete?.setOnMenuItemClickListener(this)
//
//            save?.setOnMenuItemClickListener(this)
//        }
//
//        override fun onClick(p0: View?) {
//            if (listener != null) {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    listener!!.onItemClick(position)
//                }
//            }
//        }
    }
}