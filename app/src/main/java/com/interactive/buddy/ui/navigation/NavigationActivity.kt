package com.interactive.buddy.ui.navigation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.interactive.buddy.R
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.databinding.ActivityNavigationBinding
import com.interactive.buddy.services.ChatService
import com.interactive.buddy.services.MoodService
import kotlinx.android.synthetic.main.activity_navigation.*


class NavigationActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var binding: ActivityNavigationBinding
    private var isFABOpen: Boolean = false
    var chatService: ChatService? = null
    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = ActivityNavigationBinding.inflate(layoutInflater)
         setContentView(binding.root)

        navView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_navigation) as NavHostFragment
        navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_chats, R.id.navigation_requests, R.id.navigation_mood_selector, R.id.navigation_karma, R.id.navigation_profile))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        this.chatService = ChatService(this);
        loadKarma()

        nav_view.background = null
        val moodSelectFab: FloatingActionButton = binding.moodSelectFab
        moodSelectFab.setBackgroundResource(
            when (SharedPrefManager.getInstance(this).user.mood) {
            1 -> R.drawable.ic_smiley_sad
            2 -> R.drawable.ic_smiley_ok
            3 -> R.drawable.ic_smiley_happy
                else -> R.drawable.ic_smiley_happy
            })
        fab_sad.alpha = 0f;
        fab_sad.translationY = 100f;
        fab_sad.translationX = 100f;
        fab_ok.alpha = 0f;
        fab_ok.translationY = 150f;
        fab_happy.alpha = 0f;
        fab_happy.translationY = 100f;
        fab_happy.translationX = -100f;
        textMoodSelect.alpha = 0f;
        val service = MoodService(this)

        fab_sad.setOnClickListener {
            val user = SharedPrefManager.getInstance(this).user;
            user.mood = 1
            SharedPrefManager.getInstance(this).userLogin(user)
            service.changeMood({},{})
            updateMood()
        }
        fab_ok.setOnClickListener {
            val user = SharedPrefManager.getInstance(this).user;
            user.mood = 2
            SharedPrefManager.getInstance(this).userLogin(user)
            service.changeMood({},{})
            updateMood()
        }
        fab_happy.setOnClickListener {
            val user = SharedPrefManager.getInstance(this).user;
            user.mood = 3
            SharedPrefManager.getInstance(this).userLogin(user)
            service.changeMood({},{})
            updateMood()
        }
        moodSelectFab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }
    }

    private fun updateMood(){
        Log.d("frog", SharedPrefManager.getInstance(this).user.mood.toString())
        moodSelectFab.foreground=
            when (SharedPrefManager.getInstance(this).user.mood) {
                1 -> AppCompatResources.getDrawable(this, R.drawable.ic_smiley_sad)
                2 -> AppCompatResources.getDrawable(this, R.drawable.ic_smiley_ok)
                3 -> AppCompatResources.getDrawable(this, R.drawable.ic_smiley_happy)
                else -> AppCompatResources.getDrawable(this, R.drawable.ic_smiley_happy)
            }
        closeFABMenu()
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
        textMoodSelect.animate().alpha(1f).setDuration(500);
    }

    private fun closeFABMenu() {
        isFABOpen = false
        fab_sad.animate().alpha(0f).translationYBy(100f).translationXBy(100f).setDuration(500);
        fab_ok.animate().alpha(0f).translationYBy(150f).setDuration(500);
        fab_happy.animate().alpha(0f).translationYBy(100f).translationXBy(-100f).setDuration(500);
        textMoodSelect.animate().alpha(0f).setDuration(500);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navController.popBackStack()
        return super.onOptionsItemSelected(item);
    }

    private fun loadKarma() {
        chatService!!.getKarma(
            SharedPrefManager.getInstance(this).user.userId,
        { karma ->
            Log.d("Karma", karma)
            val menu: Menu = navView.menu
            menu.findItem(R.id.navigation_karma).title = karma
            SharedPrefManager.getInstance(applicationContext).karma = karma
        },
        {

        })
    }
}