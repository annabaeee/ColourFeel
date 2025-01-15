package at.ac.fhstp.colourfeel

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewPager2
        viewPager = findViewById(R.id.viewPager)

        // Set up the ViewPager2 with the adapter
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Initialize the ImageButtons to navigate between pages
        val buttonPage1 = findViewById<ImageButton>(R.id.buttonPage1)
        val buttonPage2 = findViewById<ImageButton>(R.id.buttonPage2)
        val buttonPage3 = findViewById<ImageButton>(R.id.buttonPage3)

        // ImageButton click listeners to switch between pages
        buttonPage1.setOnClickListener {
            viewPager.currentItem = 0  // Go to page 1
        }

        buttonPage2.setOnClickListener {
            viewPager.currentItem = 1  // Go to page 2
        }

        buttonPage3.setOnClickListener {
            viewPager.currentItem = 2  // Go to page 3
        }
    }
}
