package com.devmeist3r.taskapp.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmeist3r.taskapp.R
import com.devmeist3r.taskapp.databinding.FragmentTodoBinding
import com.devmeist3r.taskapp.ui.adapter.TaskAdapter
import com.devmeist3r.taskapp.ui.data.model.Task
import com.devmeist3r.taskapp.util.getTasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecyclerView()

        reference = Firebase.database.reference
        auth = Firebase.auth

        getTasks()
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }

    // Equivalente o cellForRow
    private fun initRecyclerView() {
        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)
        }

        with(binding.rvTasks) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun optionSelected(task: Task, option: Int) {
        when(option) {
            TaskAdapter.SELECT_REMOVE -> {
                makeToast(requireContext(), "Remover: ${task.description}")
            }
            TaskAdapter.SELECT_EDIT -> {
                makeToast(requireContext(), "Editar: ${task.description}")
            }
            TaskAdapter.SELECT_DETAILS -> {
                makeToast(requireContext(), "Detalhes: ${task.description}")
            }
            TaskAdapter.SELECT_NEXT -> {
                makeToast(requireContext(), "Proximo: ${task.description}")
            }
            TaskAdapter.SELECT_BACK -> {
                makeToast(requireContext(), "Voltar: ${task.description}")
            }
        }
    }

    private fun getTasks() {
        reference
            .child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<Task>()

                    for (ds in snapshot.children) {
                        val task = ds.getValue(Task::class.java) as Task
                        taskList.add(task)
                    }

                    taskAdapter.submitList(taskList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun makeToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}