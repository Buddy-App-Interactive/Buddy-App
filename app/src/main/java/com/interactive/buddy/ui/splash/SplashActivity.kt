package com.interactive.buddy.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.interactive.buddy.R
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.ui.login.LoginRegisterActivity
import com.interactive.buddy.ui.navigation.NavigationActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(SharedPrefManager.getInstance(this).isLoggedIn) {
            val myIntent = Intent(this, NavigationActivity::class.java)
            this.startActivity(myIntent)
        }else{
            val myIntent = Intent(this, LoginRegisterActivity::class.java)
            this.startActivity(myIntent)
        }
    }
}