package br.com.devmeist3r.bancoapp.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.devmeist3r.bancoapp.databinding.FragmentHomeBinding
import br.com.devmeist3r.bancoapp.databinding.FragmentRecoverBinding
import br.com.devmeist3r.bancoapp.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}