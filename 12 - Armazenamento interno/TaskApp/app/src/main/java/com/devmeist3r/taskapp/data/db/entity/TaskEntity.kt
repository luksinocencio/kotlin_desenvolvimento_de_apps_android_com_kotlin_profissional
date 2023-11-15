package com.devmeist3r.taskapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmeist3r.taskapp.data.model.Task
import com.devmeist3r.taskapp.ui.model.Status


@Entity(tableName = "task_table")
class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val status: Status
)

fun Task.toTaskEntity(): TaskEntity {
    return with(this) {
        TaskEntity(
            id = this.id,
            description = this.description,
            status = this.status
        )
    }
}