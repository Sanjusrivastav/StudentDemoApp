package com.example.studentdemoapp.activities.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentdemoapp.R
import com.example.studentdemoapp.activities.addstudent.AddStudent
import com.example.studentdemoapp.activities.login.LoginActivity
import com.example.studentdemoapp.databinding.ActivityHomeBinding
import com.example.studentdemoapp.localdb.students.Student
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.internal.ContextUtils.getActivity


class HomeActivity : AppCompatActivity(),
  androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
  lateinit var binding: ActivityHomeBinding
  lateinit var viewModel: HomeViewModel
  lateinit var adapter: StudentsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = ViewModelProvider(
      this,
      HomeViewModelFactory(this.application)
    )[HomeViewModel::class.java]

    adapter = StudentsAdapter(
      context = this,
      students = emptyList(),
      listener = object : StudentAdapterListener {
        override fun onDeleteClick(student: Student) {
          viewModel.deleteStudent(student)
        }

        override fun onEditClick(student: Student) {
          openEditBottomSheet(student)
        }
      })

    binding.recyclerView.layoutManager = LinearLayoutManager(this)
    binding.recyclerView.adapter = adapter


    addViewModelObservers()
    registerClickListeners()
  }

  override fun onResume() {
    super.onResume()
    viewModel.fetchAllStudents()
  }

  private fun registerClickListeners() {
    binding.menu.setOnMenuItemClickListener(this)
  }

  private fun addViewModelObservers() {
    viewModel.toastMsz.observe(this) {
      it?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
    }

    viewModel.students.observe(this) {
      adapter.updateStudents(it)
    }
  }

  override fun onMenuItemClick(item: MenuItem?): Boolean {
    if (item?.itemId == R.id.menu_add_student) {
      launchAddStudent()
    } else if (item?.itemId == R.id.menu_logout) {
      viewModel.logout()
      launchLogin()
    }
    return true
  }

  private fun launchAddStudent() {
    val intent = Intent(this, AddStudent::class.java)
    startActivity(intent)
  }

  private fun launchLogin() {
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
  }

  private fun openEditBottomSheet(student: Student) {
    val dialog = BottomSheetDialog(this@HomeActivity)
    dialog.setContentView(R.layout.activity_add_student)
    dialog.setCancelable(true)
    val submit = dialog.findViewById<Button>(R.id.button_submit)
    val name = dialog.findViewById<EditText>(R.id.edit_text_name)
    val email = dialog.findViewById<EditText>(R.id.edit_text_email)
    val age = dialog.findViewById<EditText>(R.id.edit_text_age)
    val section = dialog.findViewById<EditText>(R.id.edit_text_section)
    name?.setText(student.name)
    email?.setText(student.email)
    age?.setText(student.age.toString())
    section?.setText(student.section)

    submit?.setOnClickListener {
      val updatedStudent = Student (
        rollNumber= student.rollNumber,
        name = name?.text.toString(),
        email = email?.text.toString(),
        age = age?.text.toString().toInt(),
        section = section?.text.toString()
      )

      viewModel.updateStudent(updatedStudent)
      dialog.dismiss()
    }

    dialog.show()
  }
}