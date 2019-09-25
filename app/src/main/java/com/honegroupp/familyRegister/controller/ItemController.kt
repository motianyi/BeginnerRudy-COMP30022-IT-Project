package com.honegroupp.familyRegister.controller

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.honegroupp.familyRegister.R
import com.honegroupp.familyRegister.backend.FirebaseDatabaseManager
import com.honegroupp.familyRegister.model.Family
import com.honegroupp.familyRegister.model.Item
import com.honegroupp.familyRegister.model.User
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class is responsible for controller the event related to item.
 *
 * */
class ItemController {
    companion object {

        //TODO 1 user could create one item each time

        /**
         * This methods is responsible for creating a item and upload it to the database.
         *
         * */
        fun createItem(
            mContext: AppCompatActivity,
            itemName: EditText,
            itemDescription: EditText,
            uid: String,
            categoryName: String,
            imageURLs: ArrayList<String>,
            isPublic: Boolean
        ) {

            val item = Item(
                itemName = itemName.text.toString(),
                itemDescription = itemDescription.text.toString(),
                itemOwnerUID = uid,
                imageURLs = imageURLs,
                isPublic = isPublic
            )
            item.store(uid, categoryName)

            Toast.makeText(mContext, "Item Stored successfully", Toast.LENGTH_SHORT).show()
            // Go back to the previous activity
            mContext.finish()
        }

    }
}