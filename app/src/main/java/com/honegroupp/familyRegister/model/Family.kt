package com.honegroupp.familyRegister.model

import com.google.firebase.database.PropertyName
import com.honegroupp.familyRegister.backend.FirebaseDatabaseManager

/**
 * This class is responsible for storing data and business logic for Family
 *
 * @param familyId is the id of the family, this is generated by FirebaseAuthetication.
 *
 *
 * @author Tianyi Mo
 * */

data class Family(
    @set:PropertyName("familyName")
    @get:PropertyName("familyName")
    var familyName: String = "",
    @set:PropertyName("familyId")
    @get:PropertyName("familyId")
    var familyId: String = "",
    @set:PropertyName("familyOwner")
    @get:PropertyName("familyOwner")
    var familyOwner: String = "",
    @set:PropertyName("members")
    @get:PropertyName("members")
    var members: List<String> = emptyList()
) {
    /*This constructor has no parameter, which is used to create CategoryUpload while retrieve data from database*/
    constructor() : this("","","", listOf(""))

    fun store(uid:String){
        FirebaseDatabaseManager.uploadFamily(uid,this)
    }

}