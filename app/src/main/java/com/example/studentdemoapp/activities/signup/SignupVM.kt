package com.example.studentdemoapp.activities.signup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentdemoapp.localdb.RoomDbManager
import com.example.studentdemoapp.localdb.user.User


class SignupVM(val context: Application): ViewModel() {
  var isSignedUp: MutableLiveData<Boolean> = MutableLiveData(false)
  var emailErrorMsz: MutableLiveData<String?> = MutableLiveData(null)
  var passWordErrorMsz: MutableLiveData<String?> = MutableLiveData(null)
  var nameErrorMsz: MutableLiveData<String?> = MutableLiveData(null)
  var roomDbManager = RoomDbManager(context)

  fun signup(name: String?, email: String?, pass: String?) {
    if (name.isNullOrBlank()) {
      nameErrorMsz.value = "Name can not be empty"
    } else if (email.isNullOrBlank() || !isEmailValid(email)) {
      emailErrorMsz.value = "Email is invalid"
    } else if (pass.isNullOrBlank() || !isPasswordAlphanumeric(pass)) {
      passWordErrorMsz.value = "Password is invalid, please enter alphanumeric of length 8 with special symbols"
    } else {
      val user = User(name = name,email = email, pass = pass)
      roomDbManager.saveUser(user)
      isSignedUp.value = true
    }
  }

  private fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
  }

  private fun isPasswordAlphanumeric(password: String): Boolean {
    val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$")
    return regex.matches(password)
  }
}

class SignUPVMFactory(application: Application) :
  ViewModelProvider.Factory {
  private val mApplication: Application
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return SignupVM(mApplication) as T
  }

  init {
    mApplication = application
  }
}