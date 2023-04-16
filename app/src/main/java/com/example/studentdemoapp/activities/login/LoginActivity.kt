package com.example.studentdemoapp.activities.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.studentdemoapp.activities.home.HomeActivity
import com.example.studentdemoapp.activities.signup.SignupActivity
import com.example.studentdemoapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

  lateinit var binding : ActivityLoginBinding
  lateinit var loginViewModel: LoginViewModel
  private var MY_CAMERA_REQUEST_CODE = 100;
  private var TAG = LoginActivity::class.java.name

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    loginViewModel = ViewModelProvider(this,
      LoginVMFactory(this.application)
    )[LoginViewModel::class.java]

    addViewModelObservers()
    registerClickListeners()
    checkCameraPermission()
  }

  private fun registerClickListeners() {
    binding.btnSignup.setOnClickListener {
      launchSignup()
    }

    binding.btnLogin.setOnClickListener {
      val userName = binding.etEmail.text.toString()
      val password = binding.etPassword.text.toString()
      val isRememberMeCheck = binding.cbRememberMe.isChecked
      loginViewModel.login(userName, password, isRememberMeCheck)
    }
  }

  private fun addViewModelObservers() {
    loginViewModel.isLoggedIn.observe(this) { isLogin ->
      if (isLogin) {
        launchHome()
      }
    }

    loginViewModel.emailErrorMsz.observe(this) { errorMsg ->
      errorMsg?.let {
        binding.etEmail.error = it
      }
    }

    loginViewModel.passWordErrorMsz.observe(this) { errorMsg ->
      errorMsg?.let {
        binding.etPassword.error = it
      }
    }

    loginViewModel.toastMessage.observe(this) {
      it?.let {
        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
      }
    }

    loginViewModel.email.observe(this) {
      binding.etEmail.setText(it)
    }

    loginViewModel.password.observe(this) {
      binding.etPassword.setText(it)
    }
  }

  private fun launchHome() {
    val mIntent = Intent(this, HomeActivity::class.java)
    startActivity(mIntent)
    finish()
  }

  private  fun launchSignup() {
    val mIntent = Intent(this, SignupActivity::class.java)
    startActivity(mIntent)
  }

  private fun checkCameraPermission() {
    val permission = ContextCompat.checkSelfPermission(this,
      Manifest.permission.CAMERA)

    if (permission != PackageManager.PERMISSION_GRANTED) {
      Log.i("LoginActivity", "Permission to record denied")
      makeRequest()
    }
  }

  private fun makeRequest() {
    ActivityCompat.requestPermissions(this,
      arrayOf(Manifest.permission.CAMERA),
      MY_CAMERA_REQUEST_CODE)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
      MY_CAMERA_REQUEST_CODE -> {
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

          Log.i(TAG, "Permission has been denied by user")
        } else {
          Log.i(TAG, "Permission has been granted by user")
        }
      }
    }
  }
}