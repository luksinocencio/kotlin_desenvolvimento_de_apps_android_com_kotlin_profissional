package br.com.devmeist3r.bancoapp.presenter.auth.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.devmeist3r.bancoapp.R
import br.com.devmeist3r.bancoapp.databinding.FragmentRecoverBinding
import br.com.devmeist3r.bancoapp.util.StateView
import br.com.devmeist3r.bancoapp.util.initToolbar
import br.com.devmeist3r.bancoapp.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverFragment : Fragment() {
    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!
    private val recoverViewModel: RecoverViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.btnSend.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            recoverUser(email)
        } else {
            showBottomSheet(message = getString(R.string.text_email_empty))
        }
    }

    private fun recoverUser(email: String) {
        recoverViewModel.recover(email).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.circularProgressIndicator.isVisible = true
                }

                is StateView.Sucess -> {
                    binding.circularProgressIndicator.isVisible = false
                    showBottomSheet(
                        message = getString(R.string.text_message_send_success_recover_fragment)
                    )
                }

                is StateView.Error -> {
                    binding.circularProgressIndicator.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}