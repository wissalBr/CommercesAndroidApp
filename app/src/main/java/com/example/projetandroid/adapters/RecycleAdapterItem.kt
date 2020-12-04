package com.example.projetandroid.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.projetandroid.ItemDetailsActivity
import com.example.projetandroid.MainActivity
import com.example.projetandroid.R
import com.example.projetandroid.entities.Categorie
import com.example.projetandroid.entities.Item
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class RecycleAdapterItem(private val context: Context, var itemList: List<Item>) :
    RecyclerView.Adapter<RecycleAdapterItem.MyViewHolder>() {

    private var item: Item? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var image: ImageView = view.findViewById(R.id.item_categorie_image) as ImageView
        var id_carto: TextView = view.findViewById(R.id.id_carto) as TextView
        var activity: TextView = view.findViewById(R.id.activity) as TextView
        var address: TextView = view.findViewById(R.id.address) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return MyViewHolder(itemView)
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        item = itemList[position]

        holder.id_carto.setText(item!!.getFields()!!.getId_carto())
        holder.activity.setText(item!!.getFields()!!.getActivite_precise_du_locataire())
        holder.address.setText(item!!.getFields()!!.getAdresse())

        var imageUrl: String? = Categorie.getImageFromItem(MainActivity.categories, item!!)
        Picasso.with(context).load(imageUrl).into(holder.image)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val preferences = context.getSharedPreferences("DATA", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("selected_item", Gson().toJson(itemList[position]))
            editor.apply()
            val intent = Intent(context, ItemDetailsActivity::class.java)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}