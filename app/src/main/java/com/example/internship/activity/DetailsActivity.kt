package com.example.internship.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.internship.databinding.ActivityDetailsBinding
import com.google.android.material.tabs.TabLayout
import com.example.internship.R
import com.example.internship.adapter.FragmentPageAdapter
import com.example.internship.model.Internship
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener { onBackPressed() }

        val intent = intent

        val item = intent.getSerializableExtra("item") as? Internship

        if (item != null) {
            Picasso.get().load(item.imageUrl).into(binding.imageDetails)
            binding.titleDetails.text = item.title
            binding.duration.text = item.duration
            binding.locationDetails.text = item.location
        }

        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager)

        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle, item)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.requirements))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.benefit))

        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null){
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        binding.applyButton.setOnClickListener{
            val intent = Intent(this@DetailsActivity, ApplyActivity::class.java)
            intent.putExtra("item", item)
            startActivity(intent)
        }

    }
}