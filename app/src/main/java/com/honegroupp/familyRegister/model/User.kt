package com.honegroupp.familyRegister.model

import com.google.firebase.database.PropertyName
import com.honegroupp.familyRegister.backend.FirebaseDatabaseManager

/**
 * This class is responsible for storing data and business logic for User
 *
 * @param uid the user id of the user, this is generated by FirebaseAuthetication.
 *
 *
 * @author Renjie Meng
 * */

data class User(
    @set:PropertyName("familyId")
    @get:PropertyName("familyId")
    var familyId: String = "",
    @set:PropertyName("isFamilyOwner")
    @get:PropertyName("isFamilyOwner")
    var isFamilyOwner: Boolean = false
    ) {
    /*This constructor has no parameter, which is used to create CategoryUpload while retrieve data from database*/
    constructor() : this("")

    fun store(uid:String){
        FirebaseDatabaseManager.uploadUser(uid,this)
    }
}