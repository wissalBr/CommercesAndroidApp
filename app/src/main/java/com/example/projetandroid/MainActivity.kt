package com.example.projetandroid

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projetandroid.entities.Categorie
import com.example.projetandroid.entities.Item
import com.example.projetandroid.helpers.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val FINAL_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private var mLocationPermissionsGranted: Boolean? = false

    companion object {
        var items: List<Item> = ArrayList<Item>()
        var categories: List<Categorie> = ArrayList<Categorie>()
        var categorie: Categorie? = null
    }

    var sectionsPagerAdapter: SectionsPagerAdapter? = null
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)

        val preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE)
        categorie = Gson().fromJson(
            preferences.getString("selected_category", null),
            Categorie::class.java
        )

        loadData()

        getLocationPermission()
    }

    private fun getLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                FINAL_LOCATION
            ) === PackageManager.PERMISSION_GRANTED
        ) {

            if (ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    COARSE_LOCATION
                ) === PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionsGranted = true
                loadData()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    fun loadData() {
        val queue = Volley.newRequestQueue(this)
        var url = ""
        if (categorie === null)
            url =
                "https://opendata.paris.fr/api/records/1.0/search/?dataset=commerces-semaest&rows=0&facet=categorie_activite"
        else
            url =
                "https://opendata.paris.fr/api/records/1.0/search/?dataset=commerces-semaest&rows=311&facet=categorie_activite" +
                        "&refine.categorie_activite=" + categorie!!.getName()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                val gson = Gson()
                val jsonResponse: JSONObject
                try {
                    jsonResponse = JSONObject(response)
                    items = gson.fromJson(jsonResponse.getJSONArray("records").toString(), object :
                        TypeToken<ArrayList<Item>>() {

                    }.type)
                    val facet_groups = jsonResponse.getJSONArray("facet_groups")
                    val facets = JSONObject(facet_groups.get(0).toString())
                    categories = gson.fromJson(facets.getJSONArray("facets").toString(), object :
                        TypeToken<ArrayList<Categorie>>() {

                    }.type)

                    setImageUrls()

                    sectionsPagerAdapter =
                        SectionsPagerAdapter(applicationContext, supportFragmentManager)
                    viewPager!!.adapter = sectionsPagerAdapter
                    tabs!!.setupWithViewPager(viewPager)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(
                    applicationContext,
                    "Connection problem. Please try later!",
                    Toast.LENGTH_LONG
                ).show()
            })

        queue.add(stringRequest)
    }

    fun setImageUrls () {
        for (categorie in categories) {
            when (categorie.getName()) {
                "CULTUREL" -> categorie.setImage("https://www.perigny.fr/wp-content/uploads/2018/07/nuage-de-mots-culture.png")
                "SERVICES A LA PERSONNE" -> categorie.setImage("https://static.centreservices.fr/images/articles_cs/si.jpg")
                "METIERS D'ART" -> categorie.setImage("https://images-na.ssl-images-amazon.com/images/I/61NFORcLGwL._SX258_BO1,204,203,200_.jpg")
                "EQUIPEMENT" -> categorie.setImage("https://www.startupplace.io/wp-content/uploads/2017/07/Logo-2_300x300_acf_cropped.png")
                "ALIMENTAIRE" -> categorie.setImage("https://im.qccdn.fr/term/thematique-produit-alimentaire-55/principal-71.jpg")
                "SERVICES AUX ENTREPRISES" -> categorie.setImage("https://www.i-informatique93.fr/myshop/img/service_1.jpg")
                "TRAVAUX" -> categorie.setImage("https://www.vernaison.fr/wp-content/uploads/2018/07/travaux.jpg")
                "VACANT" -> categorie.setImage("https://st.depositphotos.com/1334572/4851/v/950/depositphotos_48515287-stock-illustration-vacant.jpg")
            }

        }
    }
}
