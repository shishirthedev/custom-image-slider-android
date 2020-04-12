package com.istytion.imageslider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import java.util.ArrayList


class ImageSliderAdapter(private val context: Context, private val images: ArrayList<String>) : PagerAdapter() {

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val myImageLayout = LayoutInflater.from(view.context).inflate(R.layout.slider_layout, view, false)
        val sliderImage = myImageLayout.findViewById<ImageView>(R.id.sliderImage)
        Picasso.with(context).load(images[position]).into(sliderImage)
        view.addView(myImageLayout, 0)
        return myImageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}

