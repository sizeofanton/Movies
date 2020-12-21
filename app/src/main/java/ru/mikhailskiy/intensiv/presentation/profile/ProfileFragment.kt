package ru.mikhailskiy.intensiv.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.extension.useDefaultDatabaseThreads
import ru.mikhailskiy.intensiv.data.room.AppDatabase
import timber.log.Timber

private const val numOfTabs = 2
class ProfileFragment : Fragment() {

    private var profilePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get()
            .load(R.drawable.ic_avatar)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.ic_avatar)
            .into(avatar)

        val profileAdapter = ProfileAdapter(
            this,
            numOfTabs
        )
        doppelgangerViewPager.adapter = profileAdapter
        doppelgangerViewPager.registerOnPageChangeCallback(profilePageChangeCallback)

        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->
            if (position == 0)
                AppDatabase.newInstance(requireContext())
                    .favorites()
                    .get()
                    .useDefaultDatabaseThreads()
                    .map { it.count() }
                    .subscribe({ itemsCount ->
                        tab.text = getString(R.string.liked, itemsCount.toString())
                    }, { throwable ->
                        Timber.e(throwable)
                    })
            if (position == 1) {
                tab.text = getString(R.string.later, 0.toString())
            }

        }.attach()
    }
}