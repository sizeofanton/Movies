package ru.mikhailskiy.intensiv.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.mikhailskiy.intensiv.presentation.watchlist.WatchlistFragment
import ru.mikhailskiy.intensiv.util.BundleProperties
import timber.log.Timber

class ProfileAdapter(fragment: Fragment, private val itemsCount: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        Timber.d("POSITION: $position")
        val bundle = Bundle().apply {
            if (position == 0)
                putString(BundleProperties.PROFILE_TAB_ID, BundleProperties.PROFILE_LIKED)
            if (position == 1)
                putString(BundleProperties.PROFILE_TAB_ID, BundleProperties.PROFILE_TO_WATCH)
        }
        return WatchlistFragment.newInstance().apply {
            arguments = bundle
        }
    }
}