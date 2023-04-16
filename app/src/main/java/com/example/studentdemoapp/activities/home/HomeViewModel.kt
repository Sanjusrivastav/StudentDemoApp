package com.example.studentdemoapp.activities.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentdemoapp.localdb.RoomDbManager
import com.example.studentdemoapp.SharedPreferenceManager
import com.example.studentdemoapp.activities.login.LoginViewModel
import com.example.studentdemoapp.localdb.students.Student

class HomeViewModel(application: Application): ViewModel() {
  var roomDbManager = RoomDbManager(application)
  var students: MutableLiveData<List<Student>> = MutableLiveData(emptyList())
  var toastMsz: MutableLiveData<String?> = MutableLiveData(null)

  fun fetchAllStudents() {
    students.value = roomDbManager.getAllStudent()
  }

  fun updateStudent(student: Student) {
    roomDbManager.updateStudent(student)
    fetchAllStudents()
  }

  fun deleteStudent(student: Student) {
    roomDbManager.deleteStudent(student)
    students.value = roomDbManager.getAllStudent()
    toastMsz.value = "Student deleted successfully"
  }

  fun logout() {
    SharedPreferenceManager.setUserLoggedOut()
  }
}

class HomeViewModelFactory(application: Application) :
  ViewModelProvider.Factory {
  private val mApplication: Application
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return HomeViewModel(mApplication) as T
  }

  init {
    mApplication = application
  }
}