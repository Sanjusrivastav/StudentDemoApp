package com.example.studentdemoapp.activities.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentdemoapp.localdb.RoomDbManager
import com.example.studentdemoapp.SharedPreferenceManager

class LoginViewModel(application: Application): ViewModel() {
  var isLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
  var emailErrorMsz: MutableLiveData<String?> = MutableLiveData(null)
  var passWordErrorMsz: MutableLiveData<String?> = MutableLiveData(null)
  var roomDbManager = RoomDbManager(application)
  var toastMessage: MutableLiveData<String?> = MutableLiveData(null)

  var email: MutableLiveData<String> = MutableLiveData(null)
  var password: MutableLiveData<String> = MutableLiveData(null)


  init {
    if (SharedPreferenceManager.isRemindMe()) {
      val lastLoginEmail = SharedPreferenceManager.getLastLoginEmail()
      lastLoginEmail?.let {
        if (it.isNotBlank() && it.isNotEmpty()) {
          val user = roomDbManager.getUserWith(it)
          email.value = user.email
          password.value = user.pass
        }
      }
    }
  }


  fun login(email: String, pass: String, isRememberMeChecked: Boolean) {
    if (!isEmailValid(email)) {
      emailErrorMsz.value = "Email is invalid"
    } else if (!isPasswordAlphanumeric(pass)) {
      passWordErrorMsz.value = "Password is invalid, please enter alphanumeric of length 8 with special symbols"
    } else {
      val user = roomDbManager.getUserWith(email)
      if (user.email == email && user.pass == pass) {
        isLoggedIn.value = true
        SharedPreferenceManager.saveRemindMe(isRememberMeChecked)
        SharedPreferenceManager.saveLastLoginUser(email)
        SharedPreferenceManager.setUserLoggedIn()
      } else {
        toastMessage.value = "email or password is incorrect"
      }
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

class LoginVMFactory(application: Application) :
  ViewModelProvider.Factory {
  private val mApplication: Application
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return LoginViewModel(mApplication) as T
  }

  init {
    mApplication = application
  }
}