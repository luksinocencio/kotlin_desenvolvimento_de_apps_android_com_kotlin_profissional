package com.devmeist3r.taskapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devmeist3r.taskapp.ui.data.model.Task
import com.devmeist3r.taskapp.util.StateView

class TaskViewModel : ViewModel() {
    private val _taskList = MutableLiveData<StateView<List<Task>>>()
    val taskList: LiveData<StateView<List<Task>>> = _taskList

    private val _taskInsert = MutableLiveData<StateView<Task>>()
    val taskInsert: LiveData<StateView<Task>> = _taskInsert

    private val _taskUpdate = MutableLiveData<StateView<Task>>()
    val taskUpdate: LiveData<StateView<Task>> = _taskUpdate

    private val _taskDelete = MutableLiveData<StateView<Task>>()
    val taskDelete: LiveData<StateView<Task>> = _taskDelete

    fun getTasks() {

    }

    fun insertTask(task: Task) {

    }


    fun updateTask(task: Task) {

    }

    fun deleteTask(task: Task) {

    }
}