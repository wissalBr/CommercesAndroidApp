package com.example.projetandroid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.projetandroid.entities.Item
import com.google.gson.Gson
import com.example.projetandroid.entities.Categorie
import com.squareup.picasso.Picasso

class ItemDetailsActivity : AppCompatActivity() {

    var detailsImageView: ImageView? = null
    var idCarto: TextView? = null
    var adresse: TextView? = null
    var cp: TextView? = null
    var ville: TextView? = null
    var operation: TextView? = null
    var activite: TextView? = null
    var categorie: TextView? = null
    var enseigne: TextView? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        detailsImageView = findViewById<ImageView>(R.id.detailsImageView)
        idCarto = findViewById<TextView>(R.id.item_id_carto)
        adresse = findViewById<TextView>(R.id.item_adresse)
        cp = findViewById<TextView>(R.id.item_cp)
        ville = findViewById<TextView>(R.id.item_ville)
        operation = findViewById<TextView>(R.id.item_operation)
        activite = findViewById<TextView>(R.id.item_activite)
        categorie = findViewById<TextView>(R.id.item_categorie)
        enseigne = findViewById<TextView>(R.id.item_enseigne)

        val preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE)
        val selectedItem =
            Gson().fromJson<Item>(preferences.getString("selected_item", null), Item::class.java)

        var imageUrl: String? = Categorie.getImageFromItem(MainActivity.categories, selectedItem)
        Picasso.with(this).load(imageUrl).into(detailsImageView)

        idCarto!!.text = selectedItem.getFields()!!.getId_carto()
        adresse!!.text = selectedItem.getFields()!!.getAdresse()
        cp!!.text = ""+(selectedItem.getFields()!!.getCp())
        ville!!.text = selectedItem.getFields()!!.getVille()
        operation!!.text = selectedItem.getFields()!!.getOperation()
        activite!!.text = selectedItem.getFields()!!.getActivite_precise_du_locataire()
        categorie!!.text = selectedItem.getFields()!!.getCategorie_activite()
        enseigne!!.text = selectedItem.getFields()!!.getEnseigne()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

