package com.devmeist3r.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.devmeist3r.fragments.databinding.FragmentABinding

class FragmentA : Fragment() {
    private var _binding: FragmentABinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentABinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        listenerFragment()
    }

    private fun initListeners() {
        val name = "Lucas Inocencio"

        binding.btnNext.setOnClickListener {
            val action = FragmentADirections
                .actionFragmentAToFragmentB(name)
            findNavController().navigate(action)
        }
    }

    private fun listenerFragment() {
        parentFragmentManager.setFragmentResultListener(
            "KEY",
            this
        ) { key, bundle ->
            val name = bundle[key].toString()
            Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}