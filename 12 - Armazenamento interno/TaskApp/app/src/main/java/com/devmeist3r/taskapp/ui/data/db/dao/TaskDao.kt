package com.devmeist3r.taskapp.ui.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.devmeist3r.taskapp.ui.data.db.entity.TaskEntity
import com.devmeist3r.taskapp.ui.data.model.Status
import com.devmeist3r.taskapp.ui.data.model.Task


interface TaskDao {
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    suspend fun getAllTask(): List<Task>

    @Insert(onConflict = IGNORE)
    suspend fun insertTask(taskEntity: TaskEntity): Long

    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteTask(id: Long)

    @Query("UPDATE task_table SET description = :description, status = :status WHERE id = :id")
    suspend fun updateTask(
        id: Long,
        description: String,
        status: Status
    )
}