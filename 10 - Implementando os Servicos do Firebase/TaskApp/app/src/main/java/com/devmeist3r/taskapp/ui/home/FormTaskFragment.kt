package com.devmeist3r.taskapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devmeist3r.taskapp.R
import com.devmeist3r.taskapp.databinding.FragmentFormTaskBinding
import com.devmeist3r.taskapp.ui.data.model.Status
import com.devmeist3r.taskapp.ui.data.model.Task
import com.devmeist3r.taskapp.ui.viewModel.TaskViewModel
import com.devmeist3r.taskapp.util.initToolbar
import com.devmeist3r.taskapp.util.makeToast
import com.devmeist3r.taskapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FormTaskFragment : Fragment() {
    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var task: Task
    private var status: Status = Status.TODO
    private var newTask: Boolean = true

    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private val args: FormTaskFragmentArgs by navArgs()
    private val viewModel: TaskViewModel by activityViewModels()

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

        reference = Firebase.database.reference
        auth = Firebase.auth

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
        binding.btnSave.setOnClickListener { validateData() }

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
            binding.progressBar.isVisible = true

            if (newTask) task = Task()
            task.id = reference.database.reference.push().key ?: ""
            task.description = description
            task.status = status

            saveTask()
        } else {
            showBottomSheet(message = getString(R.string.description_empty_form_task_fragment))
        }
    }

    private fun saveTask() {
        reference
            .child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    makeToast(requireContext(), getString( R.string.text_save_success_form_task_fragment))
                    if (newTask) {
                        findNavController().popBackStack()
                    } else {
                        viewModel.setUpdateTask(task)
                        binding.progressBar.isVisible = false
                    }
                } else {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(R.string.error_generic))
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}