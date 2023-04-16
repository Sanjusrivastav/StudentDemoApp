package com.example.studentdemoapp.localdb.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
//  @Query("SELECT * FROM user")
//  fun getUser(): List<User>

  @Query("SELECT * FROM user WHERE email LIKE :email")
  fun getUserWith(email: String): List<User>

  @Insert
  fun saveUser(vararg user: User)
}