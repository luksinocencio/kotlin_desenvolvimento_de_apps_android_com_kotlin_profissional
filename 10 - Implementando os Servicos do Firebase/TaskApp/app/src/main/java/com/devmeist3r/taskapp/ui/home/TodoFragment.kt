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
import com.devmeist3r.taskapp.ui.adapter.TaskTopAdapter
import com.devmeist3r.taskapp.ui.data.model.Status
import com.devmeist3r.taskapp.ui.data.model.Task
import com.devmeist3r.taskapp.util.getTasks

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskTopAdapter: TaskTopAdapter

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

        getTasks(taskAdapter, taskTopAdapter)
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }

    // Equivalente o cellForRow
    private fun initRecyclerView() {
        taskTopAdapter = TaskTopAdapter() { task, option ->
            optionSelected(task, option)
        }

        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)
        }

        val concatAdapter = ConcatAdapter(taskAdapter, taskTopAdapter)

        with(binding.rvTasks) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = concatAdapter
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

    private fun makeToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}