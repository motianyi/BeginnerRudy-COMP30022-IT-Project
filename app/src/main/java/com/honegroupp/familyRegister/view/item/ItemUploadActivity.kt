package com.honegroupp.familyRegister.view.item

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.honegroupp.familyRegister.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_upload_page.*
import android.app.Activity
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask


class ItemUploadActivity : AppCompatActivity(){
    val GALLERY_REQUEST_CODE = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_upload_page)

        itemChooseImage.setOnClickListener {
            selectImageInAlbum()
        }

        addItemConfirm.setOnClickListener{

        }
    }

    //use the phone API to get thr image from the album
    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    //data.getData returns the content URI for the selected Image
                    val selectedImage = data!!.data
                    itemImage.setImageURI(selectedImage)

                    //upload image to firebase storage
                    if (selectedImage != null) {
                        uploadtofirebase(selectedImage)
                    }
                }
            }
        }
    }

    private fun uploadtofirebase(selectedImage: Uri) {
        val uploadPath = " "
        val firebaseStore = FirebaseStorage.getInstance()
        val ref =
            FirebaseStorage.getInstance().reference.child(uploadPath + System.currentTimeMillis())
        var uploadTask: StorageTask<UploadTask.TaskSnapshot>? = ref.putFile(selectedImage!!)
            .addOnSuccessListener {
                //add item logic
            }
    }
}