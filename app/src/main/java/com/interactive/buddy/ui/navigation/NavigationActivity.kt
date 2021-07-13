package com.interactive.buddy.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.interactive.buddy.R
import com.interactive.buddy.databinding.ActivityNavigationBinding
import kotlinx.android.synthetic.main.activity_navigation.*


class NavigationActivity : AppCompatActivity() {

private lateinit var binding: ActivityNavigationBinding
private var isFABOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityNavigationBinding.inflate(layoutInflater)
     setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_navigation) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_chats, R.id.navigation_requests, R.id.navigation_mood_selector, R.id.navigation_karma, R.id.navigation_profile))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val moodSelectFab: FloatingActionButton = binding.moodSelectFab
        fab_sad.alpha = 0f;
        fab_sad.translationY = 100f;
        fab_sad.translationX = 100f;
        fab_ok.alpha = 0f;
        fab_ok.translationY = 150f;
        fab_happy.alpha = 0f;
        fab_happy.translationY = 100f;
        fab_happy.translationX = -100f;

        moodSelectFab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

    private fun showFABMenu() {
        isFABOpen = true
        fab_sad.animate().alpha(1f).translationYBy(-100f).translationXBy(-100f).setDuration(500);
        fab_ok.animate().alpha(1f).translationYBy(-150f).setDuration(500);
        fab_happy.animate().alpha(1f).translationYBy(-100f).translationXBy(100f).setDuration(500);
    }

    private fun closeFABMenu() {
        isFABOpen = false
        fab_sad.animate().alpha(0f).translationYBy(100f).translationXBy(100f).setDuration(500);
        fab_ok.animate().alpha(0f).translationYBy(150f).setDuration(500);
        fab_happy.animate().alpha(0f).translationYBy(100f).translationXBy(-100f).setDuration(500);
    }
}