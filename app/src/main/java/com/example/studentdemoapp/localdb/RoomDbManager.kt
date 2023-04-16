package com.example.studentdemoapp.localdb

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Room
import com.example.studentdemoapp.localdb.students.Student
import com.example.studentdemoapp.localdb.user.User

class RoomDbManager(context: Context) {

  private val db = Room.databaseBuilder(
    context,
    AppDatabase::class.java, "database-name"
  ).allowMainThreadQueries().build()

  private val userDao = db.userDao()
  private val studentDao = db.studentDao()

  fun getUserWith(email: String) = userDao.getUserWith(email).first()

  fun saveUser(user: User) = userDao.saveUser(user)

  fun saveStudent(student: Student) = studentDao.insert(student)
  fun getAllStudent() = studentDao.getAllStudents()
  fun deleteStudent(student: Student) = studentDao.delete(student)
  fun updateStudent(student: Student) = studentDao.update(student)
}