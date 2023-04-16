package com.example.studentdemoapp.activities.addstudent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.studentdemoapp.R
import com.example.studentdemoapp.activities.login.LoginVMFactory
import com.example.studentdemoapp.activities.login.LoginViewModel
import com.example.studentdemoapp.databinding.ActivityAddStudentBinding

class AddStudent : AppCompatActivity() {

  lateinit var binding: ActivityAddStudentBinding
  lateinit var viewModel: AddStudentViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAddStudentBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = ViewModelProvider(this,
      AddStudentViewModelFactory(this.application)
    )[AddStudentViewModel::class.java]

    addViewModelObservers()
    registerClickListeners()
  }

  private fun registerClickListeners() {
    binding.buttonSubmit.setOnClickListener {
      val name = binding.editTextName.text?.toString()
      val age = binding.editTextAge.text?.toString()
      val section = binding.editTextSection.text?.toString()
      val email = binding.editTextEmail.text?.toString()
      viewModel.saveStudent(name, email, age, section)
    }
  }

  private fun addViewModelObservers() {
    viewModel.successToastMsz.observe(this) {
      it?.let {
        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        finish()
      }
    }

    viewModel.nameErrorMsz.observe(this) {
      binding.editTextName.error = it
    }

    viewModel.ageErrorMsz.observe(this) {
      binding.editTextAge.error = it
    }

    viewModel.emailErrorMsz.observe(this) {
      binding.editTextEmail.error = it
    }

    viewModel.sectionErrorMsz.observe(this) {
      binding.editTextSection.error = it
    }
  }
}