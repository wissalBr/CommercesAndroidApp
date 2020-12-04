package com.example.projetandroid.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.projetandroid.ItemDetailsActivity
import com.example.projetandroid.MainActivity

import com.example.projetandroid.R
import com.example.projetandroid.entities.Item
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson

class MapsFragment : Fragment(), OnMapReadyCallback {

    var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null

    companion object {
        fun newInstance(): MapsFragment {
            val fragment = MapsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        mMapView = rootView.findViewById(R.id.mapView) as MapView
        mMapView!!.onCreate(savedInstanceState)

        mMapView!!.onResume()

        try {
            MapsInitializer.initialize(activity!!.getApplicationContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView!!.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(mMap: GoogleMap) {
        googleMap = mMap
        googleMap!!.isMyLocationEnabled = true

        for (item in MainActivity.items) {
            val latItem = LatLng(item.getFields()!!.getLat_ok()!!, item.getFields()!!.getLong_ok()!!)
            googleMap!!.addMarker(
                MarkerOptions().position(latItem)
                    .title(item.getFields()!!.getId_carto())
                    .snippet(item.getFields()!!.getActivite_precise_du_locataire())
            )
        }

        val paris = LatLng(48.8534, 2.3488)

        mMap.setOnInfoWindowClickListener { marker ->
            val itemDetails = Item.getItem(MainActivity.items, marker.title)
            val preferences = activity!!.getSharedPreferences("DATA", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("selected_item", Gson().toJson(itemDetails))
            editor.apply()
            if (itemDetails != null) {
                val intent = Intent(activity, ItemDetailsActivity::class.java)
                activity!!.startActivity(intent)
            }
        }

        // For zooming automatically to the location of Paris
        val cameraPosition = CameraPosition.Builder().target(paris).zoom(12f).build()
        googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            if (MainActivity.items.size === 0) {
                Toast.makeText(context, "Vous devez choisir une catégorie!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Vous êtes dans la catégorie : " + MainActivity.categorie!!.getName(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
