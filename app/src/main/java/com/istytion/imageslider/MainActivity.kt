package com.istytion.imageslider

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    // Image Slider
    private lateinit var sliderAdapter: ImageSliderAdapter
    private var images: ArrayList<String> = ArrayList()
    private var timer: Timer? = null
    private var currentImage = 0
    private val SLIDER_DELAY: Long = 2500.toLong()
    private val SLIDER_GAP: Long = 2500.toLong()
    private var isRegisteredSliderImageChangeListener: Boolean = false

    private val image1 = "https://filmfare.wwmindia.com/content/2020/feb/shahrukhkhan231581415073.jpg" // Shahrukh Khan
    private val image2 = "https://images.news18.com/ibnlive/uploads/2020/01/Salman-Khan-13.jpg" // Salman Khan
    private val image3 = "https://www.bollywoodhungama.com/wp-content/uploads/2020/01/Has-Aamir-Khan-shelved-his-Mahabharat.jpg" // Amir Khan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ready Image Data
        images.add(image1)
        images.add(image2)
        images.add(image3)

        // Call to Set up image data.
        setUpImageSlider()
    }

    ///////////////////// IMAGE SLIDER CODE START /////////////////////////////////
    private fun setUpImageSlider() {
        sliderAdapter = ImageSliderAdapter(this, images)
        image_slider.adapter = sliderAdapter
        if (images.size > 1) {
            tabs_dot_indicator.setupWithViewPager(image_slider, true)
            image_slider.addOnPageChangeListener(sliderImageChangeListener)
            isRegisteredSliderImageChangeListener = true

            // Auto Change of Images
            scheduleImageSlider()
        } else {
            tabs_dot_indicator.visibility = View.GONE
        }
    }

    private fun scheduleImageSlider() {
        val handler = Handler(Looper.getMainLooper())
        val updateImageRunnable = Runnable {
            image_slider.let {
                currentImage = if (currentImage == images.size) 0 else ++currentImage
                image_slider.setCurrentItem(currentImage, true)
            }
        }
        timer?.cancel() // Cancelling if timer already exists...
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(updateImageRunnable)
            }
        }, SLIDER_DELAY, SLIDER_GAP)
    }

    private val sliderImageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            currentImage = position
            scheduleImageSlider() // Rescheduling Image Slider....
        }

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    }
    ///////////////////// IMAGE SLIDER CODE END /////////////////////////////////


    override fun onDestroy() {
        if (isRegisteredSliderImageChangeListener) {
            isRegisteredSliderImageChangeListener = false
            image_slider.removeOnPageChangeListener(sliderImageChangeListener)
            timer?.cancel()
        }
        super.onDestroy()
    }
}
