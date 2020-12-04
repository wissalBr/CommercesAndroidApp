package com.example.projetandroid.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.projetandroid.MainActivity
import com.example.projetandroid.R
import com.example.projetandroid.entities.Categorie
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class RecycleAdapterCategorie(
    private val context: Context,
    private val categorieList: List<Categorie>
) : RecyclerView.Adapter<RecycleAdapterCategorie.MyViewHolder>() {

    private var categorie: Categorie? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.categorie_image) as ImageView
        var name: TextView = view.findViewById(R.id.name) as TextView
        var count: TextView = view.findViewById(R.id.count) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val categorieView = LayoutInflater.from(parent.context)
            .inflate(R.layout.categorie, parent, false)

        return MyViewHolder(categorieView)
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        categorie = categorieList[position]

        holder.name.setText(categorie!!.getName())
        holder.count.text = "Nombre d'items : " + categorie!!.getCount()

        Picasso.with(context).load(categorie!!.getImage()).into(holder.image)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val preferences = context.getSharedPreferences("DATA", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("selected_category", Gson().toJson(categorieList[position]))
            editor.apply()

            MainActivity.categorie = categorieList[position]
            (context as MainActivity).loadData()
        })
    }

    override fun getItemCount(): Int {
        return categorieList.size
    }
}
