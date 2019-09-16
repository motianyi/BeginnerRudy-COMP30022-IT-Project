package com.honegroupp.familyRegister.backend

import android.util.Log
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.honegroupp.familyRegister.model.User
import com.google.firebase.FirebaseError
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Toast
import com.honegroupp.familyRegister.model.Family
import com.google.firebase.database.DataSnapshot as DataSnapshot1


class FirebaseDatabaseManager() {
    companion object {
        val USER_PATH = "/Users/"
        val FAMILY_PATH = "/Family/"

        /**
         * This method is responsible for uploading given object to specified path of the database.
         * */
        fun uploadUser(uid: String, user: User) {
            val databaseRef = FirebaseDatabase.getInstance().getReference(USER_PATH)

            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    //Don't ignore errors!
                    Log.d("TAG", p0.message)
                }

                override fun onDataChange(p0: DataSnapshot1) {
                    var isExist = false

                    p0.children.forEach {
                        // Check if the user has already exists
                        if (it.key == uid) {
                            isExist = true
                        }
                    }

                    // if the user hasn't been recorded ,
                    // Use the uid to construct the user's uiq path on database
                    if (!isExist){
                        databaseRef.child(uid).setValue(user)
                    }

                }


            })
        }

        /**
         * This method is responsible for uploading given object to specified path of the database.
         * */
        fun uploadFamily(familyId: String, family: Family) {
            val databaseRef = FirebaseDatabase.getInstance().getReference(FAMILY_PATH)

            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    //Don't ignore errors!
                    Log.d("TAG", p0.message)
                }

                override fun onDataChange(p0: DataSnapshot1) {
                    var isExist = false

                    p0.children.forEach {
                        // Check if the family has already exists
                        if (it.key == familyId) {
                            isExist = true
                        }
                    }

                    // if the family hasn't been recorded ,
                    // Use the family to construct the family's unique path on database
                    if (!isExist){
                        databaseRef.child(familyId).setValue(familyId)
                    }

                }


            })
        }

    }
}