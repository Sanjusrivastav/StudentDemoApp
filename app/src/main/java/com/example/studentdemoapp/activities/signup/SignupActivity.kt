package com.example.studentdemoapp.activities.signup

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.studentdemoapp.databinding.ActivitySignupBinding


class SignupActivity : AppCompatActivity() {
  lateinit var binding : ActivitySignupBinding
  lateinit var signupViewModel: SignupVM
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySignupBinding.inflate(layoutInflater)
    setContentView(binding.root)
    signupViewModel = ViewModelProvider(this,
      SignUPVMFactory(this.application))[SignupVM::class.java]

    addViewModelObservers()
    registerClickListeners()
  }

  private fun registerClickListeners() {
    binding.btnLogin.setOnClickListener {
      finish()
    }

    binding.btnSignup.setOnClickListener {
      val name = binding.etName.text.toString()
      val userName = binding.etEmail.text.toString()
      val password = binding.etPassword.text.toString()
      signupViewModel.signup(name, userName, password)
    }
  }

  private fun addViewModelObservers() {
    signupViewModel.isSignedUp.observe(this) { isSignup ->
      if (isSignup) {
        Toast.makeText(this, "Signup successfully, please login now", Toast.LENGTH_LONG).show()
        this.onBackPressed()
      }
    }

    signupViewModel.nameErrorMsz.observe(this) { errorMsg ->
      errorMsg?.let {
        binding.etEmail.error = it
      }
    }
    signupViewModel.emailErrorMsz.observe(this) { errorMsg ->
      errorMsg?.let {
        binding.etEmail.error = it
      }
    }

    signupViewModel.passWordErrorMsz.observe(this) { errorMsg ->
      errorMsg?.let {
        binding.etPassword.error = it
      }
    }
  }
}