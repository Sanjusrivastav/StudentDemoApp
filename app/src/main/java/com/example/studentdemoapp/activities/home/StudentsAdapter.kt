package com.example.studentdemoapp.activities.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdemoapp.databinding.ItemStudentBinding
import com.example.studentdemoapp.localdb.students.Student

interface StudentAdapterListener {
  fun onDeleteClick(student: Student)
  fun onEditClick(student: Student)
}

class StudentsAdapter(context: Context, var students: List<Student>,val listener: StudentAdapterListener): RecyclerView.Adapter<StudentsAdapter.MyViewHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MyViewHolder {
    val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return  MyViewHolder(binding)
  }

  override fun onBindViewHolder(
    holder: MyViewHolder,
    position: Int
  ) {
  holder.bind(students[position])
  }

  override fun getItemCount(): Int {
   return students.size
  }

  fun updateStudents(student: List<Student>) {
    this.students = student
    notifyDataSetChanged()
  }

  inner class MyViewHolder(val binding: ItemStudentBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(student: Student) {
      binding.textViewName.text = student.name
      binding.textViewEmail.text = student.email
      binding.textViewSection.text = student.section
      binding.textViewAge.text = student.age.toString()

      binding.buttonDelete.setOnClickListener {
        listener.onDeleteClick(student)
      }
      binding.buttonEdit.setOnClickListener {
        listener.onEditClick(student)
      }
    }
  }
}

