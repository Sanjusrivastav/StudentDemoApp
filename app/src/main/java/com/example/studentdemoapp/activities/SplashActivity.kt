package com.example.studentdemoapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.studentdemoapp.R
import com.example.studentdemoapp.SharedPreferenceManager
import com.example.studentdemoapp.activities.home.HomeActivity
import com.example.studentdemoapp.activities.login.LoginActivity

class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    SharedPreferenceManager.init(this)
    waitAndLaunchLoginOrHome()
  }

  private fun waitAndLaunchLoginOrHome() {
    Handler(Looper.getMainLooper()).postDelayed({
      if (SharedPreferenceManager.isLogin()) {
        launchHomeScreen()
      } else {
        launchLoginScreen()
      }
    }, 2000)
  }

  private fun launchHomeScreen() {
    val mIntent = Intent(this@SplashActivity, HomeActivity::class.java)
    startActivity(mIntent)
    finish()
  }

  private fun launchLoginScreen() {
    val mIntent = Intent(this@SplashActivity, LoginActivity::class.java)
    startActivity(mIntent)
    finish()
  }
}