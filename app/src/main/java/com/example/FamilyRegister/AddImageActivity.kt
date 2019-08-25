package com.example.FamilyRegister

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class AddImageActivity : AppCompatActivity() {
    companion object {
        val IMAGE_POLDER_PATH = "images/"
    }

    val FILE_CHOOSER = 123
    var selected_img_uri: Uri? = null
    lateinit var firebaseStore: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var databaseRef: DatabaseReference
    var uploadTask: StorageTask<UploadTask.TaskSnapshot>? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference(IMAGE_POLDER_PATH)
        databaseRef = FirebaseDatabase.getInstance().getReference(IMAGE_POLDER_PATH)

        btn_choose_img.setOnClickListener {
            selectImageInAlbum()
        }

        btn_upload.setOnClickListener {
            if (uploadTask != null && uploadTask!!.isInProgress) {
                toast("Upload in progress", Toast.LENGTH_SHORT)
            } else {
                uploadImage()
            }
        }


        // When click on txt_show_upload, go to the ImagesActivity
        txt_show_upload.setOnClickListener {
            val show_upload_items = Intent(this, ImagesActivity::class.java)
            startActivity(show_upload_items)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun uploadImage() {
        when {
            // If the user press upload button before enter a file name, remind the user of this
            edit_txt_image_name.text.toString().trim() == "" -> toast(
                "Please Name the Image, Before Uploading",
                Toast.LENGTH_SHORT
            )
            selected_img_uri != null -> {
                val fileName = edit_txt_image_name.text.toString()
                val ref = FirebaseStorage.getInstance().reference.child(IMAGE_POLDER_PATH + fileName)

                // Upload image and its title to Firebase Storage
                uploadTask = ref.putFile(selected_img_uri!!)
                    .addOnSuccessListener {
                        Log.d("Image Uploader", "Successfully uploaded image: ${it.metadata?.path}")

                        Handler().postDelayed({
                            progress_bar.setProgress(0, false)
                        }, 500)

                        Toast.makeText(this, "Upload Successful", Toast.LENGTH_SHORT).show()

                        // Upload image title and its download url to Firebase Real-time Database
                        ref.downloadUrl.addOnCompleteListener() { taskSnapshot ->

                            var url = taskSnapshot.result
//                            Log.d("url by ref", "url =" + url.toString())

                            val upload = Upload(
                                edit_txt_image_name.text.toString().trim(),
                                url.toString()
                            )

                            // Store to database
                            val uploadID = databaseRef.push().key.toString()
                            // create a new entry in our database with uploadID, and set data to our upload object
                            databaseRef.child(uploadID).setValue(upload)
                        }

                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                        progress_bar.setProgress(progress.toInt(), true)
                    }

            }
            else -> toast("Please Upload an Image", Toast.LENGTH_SHORT)
        }
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, FILE_CHOOSER)
        }
    }

    fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, FILE_CHOOSER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            FILE_CHOOSER -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
//                    img_view.setImageURI(data.data)
//                    Use Picasso here since we late would upload images to Firebase with Picasso
                    selected_img_uri = data.data
                    Picasso.get().load(selected_img_uri).into(img_view)

                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun AddImageActivity.toast(mes: String, duration: Int) {
        Toast.makeText(this, mes, duration).show()
    }

}
