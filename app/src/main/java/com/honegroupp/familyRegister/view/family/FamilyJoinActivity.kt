package com.honegroupp.familyRegister.view.family

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.honegroupp.familyRegister.R
import com.honegroupp.familyRegister.controller.FamilyController
import kotlinx.android.synthetic.main.join_family_main.*

/**
 * This class is responsible for the join family event.
 *
 * @author Renjie　Meng
 * */
class FamilyJoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_family_main)

        //get User ID
        val currUid = intent.getStringExtra("UserID")

        // Apply the function to define the logic of user join a family
        joinFamily(familyJoinConfirm, currUid)
    }

    /**
     * This function defines the high-level logic of user joins a family
     *
     * */
    private fun joinFamily(button: Button, currUid: String) {
        button.setOnClickListener {
            FamilyController.joinFamily(
                this,
                edit_text_family_name_input,
                edit_text_family_password_input,
                currUid
            )

        }
    }
}
