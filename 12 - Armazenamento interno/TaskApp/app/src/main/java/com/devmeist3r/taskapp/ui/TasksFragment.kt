package com.devmeist3r.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmeist3r.taskapp.R
import com.devmeist3r.taskapp.data.db.AppDatabase
import com.devmeist3r.taskapp.data.db.repository.TaskRepository
import com.devmeist3r.taskapp.data.model.Task
import com.devmeist3r.taskapp.databinding.FragmentTasksBinding
import com.devmeist3r.taskapp.ui.TasksFragmentDirections.*
import com.devmeist3r.taskapp.ui.adapter.TaskAdapter
import com.devmeist3r.taskapp.util.makeToast
import com.devmeist3r.taskapp.util.showBottomSheet

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

    private val taskListViewModel: TaskListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                    val database = AppDatabase.getDatabase(requireContext())
                    val repository = TaskRepository(database.taskDao())
                    @Suppress("UNCHECKED_CAST")
                    return TaskListViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    private val taskViewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                    val database = AppDatabase.getDatabase(requireContext())
                    val repository = TaskRepository(database.taskDao())
                    @Suppress("UNCHECKED_CAST")
                    return TaskViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        taskListViewModel.getAllTasks()
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            val action = actionTasksFragmentToFormTaskFragment(null)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        taskListViewModel.taskList.observe(viewLifecycleOwner) {taskList ->
            taskAdapter.submitList(taskList)
            listEmpty(taskList)
        }

        taskViewModel.taskStateData.observe(viewLifecycleOwner) {
            taskListViewModel.getAllTasks()
        }
    }

    private fun initRecyclerView() {
        taskAdapter = TaskAdapter { task, option ->
            optionSelected(task, option)
        }

        with(binding.rvTasks) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun optionSelected(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECT_REMOVE -> {
                showBottomSheet(
                    titleDialog = R.string.text_title_dialog_delete,
                    message = getString(R.string.text_message_dialog_delete),
                    titleButton = R.string.text_button_dialog_confirm,
                    onClick = {
                        taskViewModel.deleteTask(task.id)
                    }
                )
            }
            TaskAdapter.SELECT_EDIT -> {
                val action = TasksFragmentDirections.actionTasksFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }
            TaskAdapter.SELECT_DETAILS -> {
                makeToast(requireContext(), "Detalhes: ${task.description}")
            }
        }
    }

    private fun listEmpty(taskList: List<Task>) {
        binding.textInfo.text = if (taskList.isEmpty()) {
            getString(R.string.text_list_task_empty)
        } else {
            ""
        }

        binding.progressBar.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}