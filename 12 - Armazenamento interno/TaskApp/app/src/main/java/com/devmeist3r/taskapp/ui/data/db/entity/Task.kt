package com.devmeist3r.taskapp.ui.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmeist3r.taskapp.ui.data.model.Status

@Entity(tableName = "task_table")
class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val status: Status
)