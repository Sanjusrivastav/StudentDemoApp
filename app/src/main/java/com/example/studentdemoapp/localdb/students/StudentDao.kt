package com.example.studentdemoapp.localdb.students

import androidx.room.*

@Dao
interface StudentDao {
  @Insert
  fun insert(student: Student)

  @Update
  fun update(student: Student)

  @Delete
  fun delete(student: Student)

  @Query("SELECT * FROM student")
  fun getAllStudents(): List<Student>
}



