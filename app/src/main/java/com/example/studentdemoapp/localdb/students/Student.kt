package com.example.studentdemoapp.localdb.students

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
  @PrimaryKey(autoGenerate = true) val rollNumber: Int = 0,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "email") val email: String,
  @ColumnInfo(name = "age") val age: Int,
  @ColumnInfo(name = "section") val section: String
)