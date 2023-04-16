package com.example.studentdemoapp.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studentdemoapp.localdb.students.Student
import com.example.studentdemoapp.localdb.students.StudentDao
import com.example.studentdemoapp.localdb.user.User
import com.example.studentdemoapp.localdb.user.UserDao

@Database(entities = [User::class, Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun userDao(): UserDao
  abstract fun studentDao(): StudentDao
}