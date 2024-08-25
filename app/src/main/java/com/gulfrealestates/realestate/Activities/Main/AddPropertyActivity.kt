package com.gulfrealestates.realestate.Activities.Main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Const
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.Utills.LoadingDialog
import com.gulfrealestates.realestate.databinding.ActivityAddPropertyBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class AddPropertyActivity : AppCompatActivity() {

    private var context: Context = this@AddPropertyActivity

    private lateinit var binding: ActivityAddPropertyBinding

    private lateinit var loadingDialog: LoadingDialog

    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("properties")
    private var mContext = this
    private lateinit var btnUploadProperty: Button

    private lateinit var spPropertyPurpose: Spinner
    private lateinit var spPropertyType: Spinner
    private lateinit var spPropertyCity: Spinner
    private lateinit var spPropertyAreaSize: Spinner
    private lateinit var spBedrooms: Spinner
    private lateinit var spBathrooms: Spinner

    private lateinit var etPropertyTitle: EditText
    private lateinit var etTotalPrice: EditText
    private lateinit var etPropertyDescription: EditText
    private lateinit var etEmailAddress: EditText
    private lateinit var etContactNumber: EditText
    private lateinit var etContactNumber1: EditText
    private lateinit var tvAddImg: TextView

    private var propertyId = ""
    private var imgUrl = ""

    private var propertyPurpose = ""
    private var propertyType = ""
    private var city = ""
    private var areaSize = ""
    private var bedrooms = ""
    private var bathrooms = ""

    private var title = ""
    private var totalPrice = ""
    private var propertyDescription = ""
    private var emailAddress = ""
    private var contactNumber = ""
    private var contactNumber1 = ""


    private val requestPickImage = 2
    private val cameraPermissionCode = 101
    private val requestImageCapture = 1
    private val galleryRequestCode = 2

    private var storageRef = FirebaseStorage.getInstance().reference.child("Property Pictures")

    private var selectedFileURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        clickListeners()

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun initUI() {


        loadingDialog = LoadingDialog(this)

        Functions.setTransparentStatusBar(context)

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               refreshHomeFragment()
            }
        })

        spPropertyPurpose = findViewById(R.id.sp_property_purpose)
        spPropertyType = findViewById(R.id.sp_property_type)
        spPropertyCity = findViewById(R.id.sp_property_city)
        spPropertyAreaSize = findViewById(R.id.sp_area_size)
        spBathrooms = findViewById(R.id.sp_bathrooms)
        spBedrooms = findViewById(R.id.sp_bed_rooms)

        etPropertyTitle = findViewById(R.id.et_property_title)
        etTotalPrice = findViewById(R.id.et_total_price)
        etPropertyDescription = findViewById(R.id.et_property_description)
        etEmailAddress = findViewById(R.id.et_email)
        etContactNumber = findViewById(R.id.et_contact_number)
        etContactNumber1 = findViewById(R.id.et_contact_number_1)

        btnUploadProperty = findViewById(R.id.btnUploadProperty)

        tvAddImg = findViewById(R.id.tv_add_img)

    }

    private fun clickListeners() {

        val propertyTitleAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_property_title_items,
            android.R.layout.simple_spinner_item
        )


        tvAddImg.setOnClickListener {

            val options = arrayOf("Camera", "Gallery")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose Option")
            builder.setItems(options) { dialog, which ->

                when (which) {
                    1 -> openGallery()    // Open gallery for images

                    0 -> {
                        if (checkCameraPermission()) {

                            openCamera()

                        } else {

                            requestCameraPermission()

                        }
                    }

                    1 -> {
                        val pickPhotoIntent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhotoIntent, requestPickImage)
                    }
                }
            }
            builder.show()
        }

        btnUploadProperty.setOnClickListener {

            validateInputs()

        }

    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, requestImageCapture)
        }
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraPermissionCode
            )
        } else {

            openCamera()
        }
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.ivImg.visibility = View.VISIBLE
                binding.llImg.visibility = View.GONE
                Glide.with(mContext).load(uri).into(binding.ivImg)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == Const.PICK_FILE_REQUEST_CODE) {
            data?.data?.let { uri ->
                if (!uri.path.isNullOrEmpty()) {
                    selectedFileURI = uri
                    binding.ivImg.visibility = View.VISIBLE
                    binding.llImg.visibility = View.GONE
                    Glide.with(mContext).load(uri).into(binding.ivImg)
                }
            }
        }
    }


    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, galleryRequestCode)
    }

    private fun validateInputs() {

        propertyPurpose = spPropertyPurpose.selectedItem.toString()
        propertyType = spPropertyType.selectedItem.toString()
        city = spPropertyCity.selectedItem.toString()
        areaSize = spPropertyAreaSize.selectedItem.toString()
        bedrooms = spBedrooms.selectedItem.toString()
        bathrooms = spBathrooms.selectedItem.toString()

        title = etPropertyTitle.text.toString().trim()
        totalPrice = etTotalPrice.text.toString().trim()
        propertyDescription = etPropertyDescription.text.toString().trim()
        emailAddress = etEmailAddress.text.toString().trim()
        contactNumber = etContactNumber.text.toString().trim()
        contactNumber1 = etContactNumber1.text.toString().trim()

        // Add Validations here when all field will be validated then call uploadPropertyImage() function

        uploadPropertyImage()

    }

    private fun uploadPropertyImage() {

        propertyId = databaseReference.push().key.toString()

        val filePath: StorageReference = storageRef.child("$propertyId.jpg")

        loadingDialog.show()

        filePath.putFile(selectedFileURI!!)
            .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                val firebaseUri =
                    taskSnapshot.storage.downloadUrl
                firebaseUri.addOnSuccessListener { uri: Uri ->

                    selectedFileURI = null
                    imgUrl = uri.toString()

                    uploadProperty()

                }.addOnFailureListener { e: Exception ->

                    loadingDialog.dismiss()
                    Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener { e: Exception ->

                loadingDialog.dismiss()
                Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()

            }

    }

    private fun uploadProperty() {
        // Get user input values


        // Create property object
        val property = Property(
            propertyId,
            propertyPurpose,
            propertyType,
            city,
            areaSize,
            bedrooms,
            bathrooms,
            title,
            totalPrice,
            propertyDescription,
            emailAddress,
            contactNumber,
            contactNumber1,
            imgUrl
        )

        databaseReference.child(propertyId).setValue(property)
            .addOnSuccessListener {

                loadingDialog.dismiss()

                Toast.makeText(this, "Property uploaded successfully", Toast.LENGTH_SHORT)
                    .show()

                refreshHomeFragment()
                finish() // Close activity after uploading

            }
            .addOnFailureListener {

                loadingDialog.dismiss()
                Toast.makeText(this, "Failed to upload property", Toast.LENGTH_SHORT).show()

            }

    }

    private fun refreshHomeFragment() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("refreshHomeFragment", true)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

//    override fun onBackPressed() {
//        refreshHomeFragment()
//        finish()
//    }

}


