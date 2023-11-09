package com.devmeist3r.taskapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmeist3r.taskapp.R
import com.devmeist3r.taskapp.databinding.FragmentTasksBinding
import com.devmeist3r.taskapp.ui.adapter.TaskAdapter
import com.devmeist3r.taskapp.ui.data.model.Status
import com.devmeist3r.taskapp.ui.data.model.Task
import com.devmeist3r.taskapp.ui.viewModel.TaskViewModel
import com.devmeist3r.taskapp.util.StateView
import com.devmeist3r.taskapp.util.makeToast
import com.devmeist3r.taskapp.util.showBottomSheet

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel: TaskViewModel by activityViewModels()

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
        viewModel.getTasks()
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            val action = TasksFragmentDirections
                .actionTasksFragmentToFormTaskFragment(null)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.taskList.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.OnLoading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.OnSucess -> {

                    val taskList = stateView.data?.filter { it.status == Status.TODO }

                    binding.progressBar.isVisible = false
                    listEmpty(taskList ?: emptyList())

                    taskAdapter.submitList(taskList)
                }
                is StateView.OnError -> {
                    Toast.makeText(requireContext(), stateView.massage, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        }

        viewModel.taskInsert.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.OnLoading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.OnSucess -> {
                    binding.progressBar.isVisible = false

                    if (stateView.data?.status == Status.TODO) {
                        // Armazena a lista atual do adapter
                        val oldList = taskAdapter.currentList

                        // Gera uma nova lista a partir da lista antiga já com a tarefa atualizada
                        val newList = oldList.toMutableList().apply {
                            add(0, stateView.data)
                        }

                        // Envia a lista atualizada para o adapter
                        taskAdapter.submitList(newList)

                        setPositionRecyclerView()
                    }

                }
                is StateView.OnError -> {
                    Toast.makeText(requireContext(), stateView.massage, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        }

        viewModel.taskUpdate.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.OnLoading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.OnSucess -> {
                    binding.progressBar.isVisible = false
                    val oldList = taskAdapter.currentList
                    val newList = oldList.toMutableList().apply {
                        if(!oldList.contains(stateView.data) && stateView.data?.status == Status.TODO){
                            add(0, stateView.data)
                            setPositionRecyclerView()
                        }

                        if (stateView.data?.status == Status.TODO) {
                            find { it.id == stateView.data.id }?.description =
                                stateView.data.description
                        } else {
                            remove(stateView.data)
                        }
                    }
                    val position = newList.indexOfFirst { it.id == stateView.data?.id }
                    taskAdapter.submitList(newList)
                    taskAdapter.notifyItemChanged(position)
                }
                is StateView.OnError -> {
                    Toast.makeText(requireContext(), stateView.massage, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        }

        viewModel.taskDelete.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.OnLoading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.OnSucess -> {
                    binding.progressBar.isVisible = false

                    Toast.makeText(
                        requireContext(),
                        R.string.text_delete_success_task,
                        Toast.LENGTH_SHORT
                    ).show()

                    val oldList = taskAdapter.currentList
                    val newList = oldList.toMutableList().apply {
                        remove(stateView.data)
                    }
                    taskAdapter.submitList(newList)
                }
                is StateView.OnError -> {
                    Toast.makeText(requireContext(), stateView.massage, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
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
                        viewModel.deleteTask(task)
                    }
                )
            }
            TaskAdapter.SELECT_EDIT -> {
                val action = TasksFragmentDirections
                    .actionTasksFragmentToFormTaskFragment(task)
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
    }

    private fun setPositionRecyclerView() {
        taskAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                binding.rvTasks.scrollToPosition(0)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {

            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {

            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.rvTasks.scrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                binding.rvTasks.scrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}