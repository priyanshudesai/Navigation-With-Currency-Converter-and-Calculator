package com.pd.currencyconverter.ui.testFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.pd.currencyconverter.databinding.ActivityTestBinding
import com.pd.currencyconverter.utils.ConstantUtils
import com.pd.currencyconverter.utils.LocaleHelper


class TestActivity : AppCompatActivity(), OneFragment.OnFragmentCommunicationListener {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {

//        currentTheme = PreferenceManager.getDefaultSharedPreferences(this@NavigationActivity)
//            .getInt(ConstantUtils.KEY_THEME, ConstantUtils.NORMAL)
        setTheme(ConstantUtils.DARK)
//        currentLanguage = PreferenceManager.getDefaultSharedPreferences(this@NavigationActivity).getString(LocaleHelper.SELECTED_LANGUAGE, "en").toString()
        LocaleHelper.setLocale(this@TestActivity)

        super.onCreate(savedInstanceState)


        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sectionsPagerAdapter = SectionsPagerAdapter(
            supportFragmentManager,
            lifecycle
        )
        val viewPager = binding.viewPagerTest
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabLayoutTest
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = if (position == 0) "ONE" else "TWO"
            viewPager.currentItem = position
        }.attach()
        viewPager.currentItem = 0
        viewPager.setPageTransformer(ZoomOutPageTransformer())
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        })
    }


    class SectionsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fm, lifecycle) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                OneFragment::class.java.newInstance() as Fragment
            } else {
                TwoFragment::class.java.newInstance() as Fragment
            }
        }
    }

    override fun onTextChange(text: String) {
        val fragment: TwoFragment =
            supportFragmentManager.findFragmentByTag("f1") as TwoFragment
        fragment.onTextChange(text)
    }


    class ZoomOutPageTransformer : ViewPager2.PageTransformer {

        companion object {
            private const val MIN_SCALE = 0.85f
            private const val MIN_ALPHA = 0.5f
        }

        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }

}