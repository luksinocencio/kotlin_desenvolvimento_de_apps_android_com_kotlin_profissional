package com.devmeist3r.taskapp.ui.data.db.repository

import com.devmeist3r.taskapp.ui.data.db.dao.TaskDao
import com.devmeist3r.taskapp.ui.data.db.entity.TaskEntity
import com.devmeist3r.taskapp.ui.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun getAllTask(): List<Task> {
        return taskDao.getAllTask()
    }

    suspend fun insertTask(taskEntity: TaskEntity): Long {
        return taskDao.insertTask(taskEntity)
    }

    suspend fun deleteTask(id: Long) = taskDao.deleteTask(id)


    suspend fun updateTask(taskEntity: TaskEntity) {
        taskDao.updateTask(
            id = taskEntity.id,
            description = taskEntity.description,
            status = taskEntity.status
        )
    }
}