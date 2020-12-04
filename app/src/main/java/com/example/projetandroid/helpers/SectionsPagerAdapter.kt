package com.example.projetandroid.helpers

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.projetandroid.R
import com.example.projetandroid.fragments.InfosFragment
import com.example.projetandroid.fragments.ListFragment
import com.example.projetandroid.fragments.MapsFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.list, R.string.map, R.string.info)
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return ListFragment.newInstance()
        else if (position == 1)
            return MapsFragment.newInstance()
        else if (position == 2)
            return InfosFragment.newInstance()

        return InfosFragment.newInstance()
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(TAB_TITLES[position])
    }
}