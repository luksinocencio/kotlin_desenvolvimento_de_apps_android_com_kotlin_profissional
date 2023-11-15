package com.devmeist3r.taskapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.devmeist3r.taskapp.data.db.entity.TaskEntity
import com.devmeist3r.taskapp.data.model.Task
import com.devmeist3r.taskapp.ui.model.Status

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTask(): List<Task>

    @Insert(onConflict = IGNORE)
    fun insertTask(taskEntity: TaskEntity): Long

    @Query("DELETE FROM task_table WHERE id = :id")
    fun deleteTask(id: Long)

    @Query("UPDATE task_table SET description = :description, status = :status WHERE id = :id")
    fun updateTask(
        id: Long,
        description: String,
        status: Status
    )

}