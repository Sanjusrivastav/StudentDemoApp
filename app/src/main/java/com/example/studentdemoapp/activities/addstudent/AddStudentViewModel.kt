package com.example.studentdemoapp.activities.addstudent

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentdemoapp.localdb.RoomDbManager
import com.example.studentdemoapp.localdb.students.Student

 class AddStudentViewModel(application: Application): ViewModel(){

  var roomDbManager = RoomDbManager(application)
  var emailErrorMsz: MutableLiveData<String?> = MutableLiveData(null)
  var ageErrorMsz: MutableLiveData<String?> = MutableLiveData(null)
  var nameErrorMsz: MutableLiveData<String?> = MutableLiveData(null)
  var sectionErrorMsz: MutableLiveData<String?> = MutableLiveData(null)

  var successToastMsz: MutableLiveData<String?> = MutableLiveData(null)

  fun saveStudent(name: String?, email: String?, age: String?, section: String?) {
    if (name.isNullOrBlank()) {
      nameErrorMsz.value = "Name can't be empty"
    } else if (email.isNullOrBlank() || !isEmailValid(email)) {
      emailErrorMsz.value = "Email invalid"
    } else if (age.isNullOrBlank() || age.toInt() <= 0) {
      ageErrorMsz.value = "age cant not be below 0"
    } else if (section.isNullOrBlank()) {
      sectionErrorMsz.value = "section can't be empty"
    } else {
      val student = Student(name = name, email = email, age = age.toInt(), section = section)
      roomDbManager.saveStudent(student)
      successToastMsz.value = "Student added successfully"
    }
  }

  private fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
  }
}

 class AddStudentViewModelFactory(application: Application) :
  ViewModelProvider.Factory {
  private val mApplication: Application
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return AddStudentViewModel(mApplication) as T
  }

  init {
    mApplication = application
  }
}
