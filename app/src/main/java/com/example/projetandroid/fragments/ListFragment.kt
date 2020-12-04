package com.example.projetandroid.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetandroid.MainActivity

import com.example.projetandroid.R
import com.example.projetandroid.adapters.RecycleAdapterCategorie
import com.example.projetandroid.adapters.RecycleAdapterItem
import com.example.projetandroid.entities.Item


class ListFragment : Fragment() {
    internal lateinit var view: View

    private var mInputRecherche: EditText? = null
    private var removeInputText: ImageView? = null
    private var searchIcon: ImageView? = null

    private var selectedCategoryLL: LinearLayout? = null
    private var selectedCategoryTextView: TextView? = null
    private var selectedCategoryImageView: ImageView? = null

    private var recyclerView: RecyclerView? = null
    private var mAdapterItem: RecycleAdapterItem? = null
    private var mAdapterCategorie: RecycleAdapterCategorie? = null

    companion object {
        fun newInstance(): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view = inflater.inflate(R.layout.fragment_list, container, false)
        mInputRecherche = view.findViewById(R.id.input_recherche) as EditText
        removeInputText = view.findViewById(R.id.cancel_icon) as ImageView
        searchIcon = view.findViewById(R.id.search_icon) as ImageView

        selectedCategoryLL = view.findViewById(R.id.selected_category) as LinearLayout
        selectedCategoryTextView = view.findViewById(R.id.selected_category_name) as TextView
        selectedCategoryImageView = view.findViewById(R.id.remove_selected_category) as ImageView

        if (MainActivity.categorie == null) {
            selectedCategoryLL!!.visibility = View.GONE

            mInputRecherche!!.isEnabled = false
            mInputRecherche!!.setHint("Veuillez sélectionner une catégorie!")

            removeInputText!!.visibility = View.GONE
            searchIcon!!.visibility = View.GONE
        } else {
            mInputRecherche!!.isEnabled = true
            mInputRecherche!!.setHint("Lancer la recherche...")

            removeInputText!!.visibility = View.VISIBLE
            searchIcon!!.visibility = View.VISIBLE

            selectedCategoryLL!!.visibility = View.VISIBLE
            selectedCategoryTextView!!.setText(MainActivity.categorie!!.getName())
            selectedCategoryImageView!!.setOnClickListener {
                val preferences = activity!!.getSharedPreferences("DATA", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("selected_category", null)
                editor.apply()

                MainActivity.categorie = null
                (activity as MainActivity).loadData()
            }
            removeInputText!!.setOnClickListener {
                mInputRecherche!!.setText("")
            }
        }

        recyclerView = view.findViewById(R.id.recyclerview_property) as RecyclerView

        if (MainActivity.categorie != null) {
            mAdapterItem = RecycleAdapterItem(activity!!, MainActivity.items)

            val mLayoutManager = LinearLayoutManager(activity)
            recyclerView!!.setLayoutManager(mLayoutManager)
            recyclerView!!.setItemAnimator(DefaultItemAnimator())
            recyclerView!!.setAdapter(mAdapterItem)
        } else {
            mAdapterCategorie = RecycleAdapterCategorie(activity!!, MainActivity.categories)

            val mLayoutManager = LinearLayoutManager(activity)
            recyclerView!!.setLayoutManager(mLayoutManager)
            recyclerView!!.setItemAnimator(DefaultItemAnimator())
            recyclerView!!.setAdapter(mAdapterCategorie)
        }
        searchInputListener()

        return view
    }

    private fun searchInputListener() {
        mInputRecherche!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                searchItems()
            }
        })
    }

    private fun searchItems() {
        if (MainActivity.categorie != null) {
            val recherche = mInputRecherche!!.text.toString()
            mAdapterItem!!.itemList = Item.searchItems(MainActivity.items, recherche)
            mAdapterItem!!.notifyDataSetChanged()
        }
    }
}
