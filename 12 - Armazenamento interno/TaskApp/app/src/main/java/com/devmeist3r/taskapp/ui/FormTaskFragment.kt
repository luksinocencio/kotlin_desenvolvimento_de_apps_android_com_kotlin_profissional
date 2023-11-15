package com.devmeist3r.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devmeist3r.taskapp.R
import com.devmeist3r.taskapp.databinding.FragmentFormTaskBinding

import com.devmeist3r.taskapp.data.model.Task
import com.devmeist3r.taskapp.data.db.AppDatabase
import com.devmeist3r.taskapp.data.db.repository.TaskRepository
import com.devmeist3r.taskapp.ui.model.Status
import com.devmeist3r.taskapp.util.initToolbar
import com.devmeist3r.taskapp.util.makeToast
import com.devmeist3r.taskapp.util.showBottomSheet

class FormTaskFragment : BaseFragment() {
    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var task: Task
    private var status: Status = Status.TODO
    private var newTask: Boolean = true

    private val args: com.devmeist3r.taskapp.ui.FormTaskFragmentArgs by navArgs()

    private val viewModel: TaskViewModel by viewModels {
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
        _binding = FragmentFormTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        getArgs()
        initListeners()
    }

    private fun getArgs() {
        args.task.let {
            if (it != null) {
                this.task = it
                configureTask()
            }
        }
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            observeViewModel()
            validateData()
        }

        binding.rgStatus.setOnCheckedChangeListener { _, id ->
            status = when (id) {
                R.id.rbTodo -> Status.TODO
                R.id.rbDoing -> Status.DOING
                else -> Status.DONE
            }
        }
    }

    private fun configureTask() {
        newTask = false
        status = task.status
        binding.textToolbar.setText(R.string.text_toolbar_update_form_task_fragment)

        binding.editDescription.setText(task.description)

        setStatus()
    }

    private fun setStatus() {
        binding.rgStatus.check(
            when (task.status) {
                Status.TODO -> R.id.rbTodo
                Status.DOING -> R.id.rbDoing
                else -> R.id.rbDone
            }
        )
    }

    private fun validateData() {
        val description = binding.editDescription.text.toString().trim()

        if (description.isNotEmpty()) {
            hideKeyboard()
            binding.progressBar.isVisible = true
            if (newTask) task = Task()
            task.description = description
            task.status = status

            if (newTask) {
                viewModel.insertOrUpdateTask(
                    description = description,
                    status = status
                )
            } else {
                viewModel.insertOrUpdateTask(
                    id = task.id,
                    description = description,
                    status = status
                )
            }
        } else {
            showBottomSheet(message = getString(R.string.description_empty_form_task_fragment))
        }
    }

    private fun observeViewModel() {
        viewModel.taskStateData.observe(viewLifecycleOwner) { stateTask ->
            if (stateTask == StateTask.Inserted || stateTask == StateTask.Update) {
                findNavController().popBackStack()
            }
        }

        viewModel.taskStateMessage.observe(viewLifecycleOwner) { message ->
            binding.progressBar.isVisible = false
            makeToast(requireContext(), getString(message))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}