package com.texttranslate.voiceimage

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.texttranslate.voiceimage.base.PrefManager
import com.texttranslate.voiceimage.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity(), View.OnClickListener {
    private var layouts: IntArray?= null
    var binding: ActivityIntroBinding? = null
    var prefManager: PrefManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        prefManager = PrefManager(this)
        if (!prefManager!!.isFirstTimeLaunch) {
            launchHomeScreen()
            finish()
        }
        layouts = intArrayOf(
            R.layout.layout_into1,
            R.layout.layout_into2,
            R.layout.layout_into3
        )
        addBottomDots(0)
        val myViewPagerAdapter = MyViewPagerAdapter()
        binding!!.mViewPager.setAdapter(myViewPagerAdapter)
        binding!!.mViewPager.addOnPageChangeListener(viewPagerPageChangeListener)
        binding!!.mTxtNext.setOnClickListener(this)
        binding!!.mTxtSkip.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view === binding!!.mTxtNext) {
            val current = item
            if (current < layouts!!.size) {
                // move to next screen
                binding!!.mViewPager.setCurrentItem(current)
            } else {
                launchHomeScreen()
            }
        } else if (view === binding!!.mTxtSkip) {
            launchHomeScreen()
        }
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(layouts!!.size)
        val colorsActive = getResources().getIntArray(R.array.array_dot_active)
        val colorsInactive = getResources().getIntArray(R.array.array_dot_inactive)
        binding!!.mLayoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 28f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            binding!!.mLayoutDots.addView(dots[i])
        }
        if (dots.size > 0) dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private val item: Int
        private get() = binding!!.mViewPager.currentItem + 1

    private fun launchHomeScreen() {
        prefManager!!.isFirstTimeLaunch = false
        startActivity(Intent(this@IntroActivity, HomeActivity::class.java))
        finish()
    }

    var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            if (position == 2) {
                binding!!.mTxtSkip.visibility = View.GONE
            } else {
                binding!!.mTxtSkip.visibility = View.VISIBLE
            }
            addBottomDots(position)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    inner class MyViewPagerAdapter : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(layouts!![position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return layouts!!.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}