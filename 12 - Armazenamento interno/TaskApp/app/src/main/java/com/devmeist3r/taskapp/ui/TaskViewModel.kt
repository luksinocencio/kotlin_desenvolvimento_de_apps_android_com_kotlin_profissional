package com.devmeist3r.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devmeist3r.taskapp.R
import com.devmeist3r.taskapp.data.db.entity.toTaskEntity
import com.devmeist3r.taskapp.data.db.repository.TaskRepository
import com.devmeist3r.taskapp.data.model.Task
import com.devmeist3r.taskapp.ui.model.Status
import com.devmeist3r.taskapp.util.StateView
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _taskStateData = MutableLiveData<StateTask>()
    val taskStateData: LiveData<StateTask> = _taskStateData

    private val _taksStateMessage = MutableLiveData<Int>()
    val taskStateMessage: LiveData<Int> = _taksStateMessage

    fun insertOrUpdateTask(id: Long = 0, description: String, status: Status) {
        if (id == 0L) {
            insertTask(Task(description = description, status = status))
        } else {
            updateTask(Task(id, description, status))
        }
    }

    fun deleteTask(id: Long) = viewModelScope.launch {
        try {
            repository.deleteTask(id)
            _taskStateData.postValue(StateTask.Delete)
            _taksStateMessage.postValue(R.string.text_delete_success_task)
        } catch (ex: Exception) {
            _taksStateMessage.postValue(R.string.text_delete_error_form_task_fragment)
        }
    }

    // MARK: - Private Function(s).
    private fun insertTask(task: Task) = viewModelScope.launch {
        try {
            val id = repository.insertTask(task.toTaskEntity())
            if (id > 0) {
                _taskStateData.postValue(StateTask.Inserted)
                _taksStateMessage.postValue(R.string.text_save_success_form_task_fragment)
            }
        } catch (ex: Exception) {
            _taksStateMessage.postValue(R.string.text_save_error_form_task_fragment)
        }
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        try {
            repository.updateTask(task.toTaskEntity())
            _taskStateData.postValue(StateTask.Update)
            _taksStateMessage.postValue(R.string.text_update_success_form_task_fragment)
        } catch (ex: Exception) {
            _taksStateMessage.postValue(R.string.text_update_error_form_task_fragment)
        }
    }
}

sealed class StateTask {
    object Inserted: StateTask()
    object Update: StateTask()
    object Delete: StateTask()

}