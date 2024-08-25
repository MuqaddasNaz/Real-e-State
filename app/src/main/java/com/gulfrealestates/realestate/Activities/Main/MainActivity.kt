package com.gulfrealestates.realestate.Activities.Main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.gulfrealestates.realestate.Activities.StartUp.LoginActivity
import com.gulfrealestates.realestate.Adapter.HomeAdapter
import com.gulfrealestates.realestate.Adapter.PagerAdapter.MainPagerAdapter
import com.gulfrealestates.realestate.Models.PropertiesItem
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.Utills.LoadingDialog
import com.gulfrealestates.realestate.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private var context: Context = this@MainActivity


    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("properties")

    private var propertyId: String = ""
    private var allTypes: String = ""
    private var allStatus: String = ""
    private var city: String = ""
    private var area: String = ""
    private var bedrooms: String = ""
    private var bathrooms: String = ""


    private var propertyList = ArrayList<Property>()
    private lateinit var adapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var loadingDialog: LoadingDialog
    private var mContext = this

    private lateinit var menuIcon: ImageButton
    private lateinit var watsappIcn: ImageView
    private lateinit var expandableMenu: LinearLayout
    private lateinit var expandableMenu2: ScrollView
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainPagerAdapter: MainPagerAdapter
    private lateinit var blogsTextView: TextView
    private lateinit var servicesTextView: TextView
    private lateinit var aboutUsTextView: TextView
    private lateinit var contactUsTextView: TextView
    private lateinit var addPropertyImageView: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("My Properties") // Initialize databaseReference

        setBottomNav()
        clickListeners()

        Functions.setTransparentStatusBar(context)

        val whatsappIcon: ImageView = findViewById(R.id.watsapp_icn)

        whatsappIcon.setOnClickListener {

            val number = "+971507961525" // WhatsApp number yahan daalein
            val url = "https://api.whatsapp.com/send?phone=$number"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "WhatsApp is  installed on your device.", Toast.LENGTH_SHORT).show()
            }
        }


        val saveSearch = findViewById<Button>(R.id.btn_saveSearch)

        saveSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }

            private fun performSearch(query: String)  {

                val filterList = mutableListOf<Property>()
                for (property in propertyList) {

                    var name = property.title.lowercase()

                    if (name.contains(query.lowercase(), ignoreCase = true)) {
                        filterList.add(property)
                    }
                }

                recyclerView.adapter = adapter
                recyclerView.adapter?.notifyDataSetChanged()            }

            override fun afterTextChanged(s: Editable?) {}
        })

        loadingDialog = LoadingDialog(this)


        menuIcon = findViewById(R.id.menu_icon)
        expandableMenu = findViewById(R.id.expandable_menu)


        menuIcon.setOnClickListener {
            toggleSideMenu()
        }

        val btnReview: FloatingActionButton = findViewById(R.id.btn_add)
        val expandableMenu2: ScrollView = findViewById(R.id.expandable_menu2)

        btnReview.setOnClickListener {
            if (expandableMenu2.visibility == View.VISIBLE) {
                expandableMenu2.visibility = View.GONE
            } else {
                expandableMenu2.visibility = View.VISIBLE
            }        }


        val addIcn: ImageView = findViewById(R.id.icn_add)

        addIcn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val tvServices: TextView = findViewById(R.id.tv_Services)

        tvServices.setOnClickListener {
            val intent = Intent(this, ServicesActivity::class.java)
            startActivity(intent)
        }

        val tvAgencies: TextView = findViewById(R.id.tv_Agencies)

        tvAgencies.setOnClickListener {
            val intent = Intent(this, AgenciesActivity::class.java)
            startActivity(intent)
        }

        val tvAboutUs: TextView = findViewById(R.id.tv_AboutUs)

        tvAboutUs.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }


        val tvHome: TextView = findViewById(R.id.tv_home)

        tvHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val tvContactUs: TextView = findViewById(R.id.tv_ContactUs)

        tvContactUs.setOnClickListener {

            val intent = Intent(this, ContactUsActivity::class.java)
            startActivity(intent)
        }
        val icDropDown = findViewById<ImageView>(R.id.ic_DropDown)
        val layout1 = findViewById<LinearLayout>(R.id.layout1)

        icDropDown.setOnClickListener {
            if (layout1.visibility == View.VISIBLE) {
                layout1.visibility = View.GONE
            } else {
                layout1.visibility = View.VISIBLE
            }
        }

        val tvResidential = findViewById<TextView>(R.id.tv_Residential)
        val tvCommercial = findViewById<TextView>(R.id.tv_Commercial)

        tvResidential.setOnClickListener {
            val intent = Intent(this, ResidentialActivity::class.java)
            startActivity(intent)
        }

        tvCommercial.setOnClickListener {
            val intent = Intent(this, CommercialActivity::class.java)
            startActivity(intent)
        }

        val tvTripBooking = findViewById<TextView>(R.id.tv_TripBooking)

        tvTripBooking.setOnClickListener {
            val intent = Intent(this, BookHotelActivity::class.java)
            startActivity(intent)
        }


    }



    private fun setBottomNav() {
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        binding.vpMain.adapter = mainPagerAdapter

        binding.vpMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNavigationView.menu.findItem(R.id.menu_home).isChecked = true
                    1 -> binding.bottomNavigationView.menu.findItem(R.id.menu_properties).isChecked = true
                    2 -> binding.bottomNavigationView.menu.findItem(R.id.menu_favorites).isChecked = true
                    3 -> binding.bottomNavigationView.menu.findItem(R.id.menu_profile).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> binding.vpMain.currentItem = 0
                R.id.menu_properties -> binding.vpMain.currentItem = 1
                R.id.menu_favorites -> binding.vpMain.currentItem = 2
                R.id.menu_profile -> binding.vpMain.currentItem = 3
            }
            true
        }
    }
    private fun clickListeners() {
        binding.tvContactUs.setOnClickListener {

            toggleSideMenu()
            startActivity(Intent(this, ContactUsActivity::class.java))

        }
        binding.btnSaveSearch.setOnClickListener {
            saveSearch()
        }

    }

    private fun saveSearch() {
        propertyId = binding.etPropertyID.text.toString().trim()
        allTypes = binding.spAllType.selectedItem.toString()
        allStatus = binding.spAllStatus.selectedItem.toString()
        city = binding.spCity.selectedItem.toString()

        uploadProperty()
    }

    private fun uploadProperty() {
        // Create com.example.realestate.Models.Property object
        val propertiesItem = PropertiesItem(
            propertyId,
            allTypes,
            allStatus,
            city,
            area,
            bedrooms,
            bathrooms
        )

        // Upload property data to Firebase database
        databaseReference.child(propertyId).setValue(propertiesItem)
            .addOnSuccessListener {

                loadingDialog.dismiss()
                Toast.makeText(this, "Property not founded", Toast.LENGTH_SHORT).show()
                refreshMyPropertiesFragment()
            }
            .addOnFailureListener { e ->

                loadingDialog.dismiss()
                Toast.makeText(this, "Failed to save property: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun refreshMyPropertiesFragment() {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("refreshMyPropertiesFragment", true)
        setResult(Activity.RESULT_OK, intent)
    }



    private fun toggleSideMenu() {
        if (expandableMenu.visibility == View.VISIBLE) {
            expandableMenu.visibility = View.GONE
        } else {
            expandableMenu.visibility = View.VISIBLE
        }
    }

    private fun openWebSite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun isDarkModeEnabled(): Boolean {
        val currentNightMode =
            this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }



}
