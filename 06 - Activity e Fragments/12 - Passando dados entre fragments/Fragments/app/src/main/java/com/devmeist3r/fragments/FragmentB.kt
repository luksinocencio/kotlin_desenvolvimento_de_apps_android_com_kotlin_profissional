package com.devmeist3r.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.devmeist3r.fragments.databinding.FragmentABinding
import com.devmeist3r.fragments.databinding.FragmentBBinding


class FragmentB : Fragment() {
    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding!!

    private val args: FragmentBArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtra()
    }

    private fun getExtra() {
        Toast.makeText(requireContext(), args.name, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}